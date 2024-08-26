package listagem.tarefas.task;

import listagem.tarefas.TestJpaConfig;
import listagem.tarefas.enums.Status;
import listagem.tarefas.model.Task;
import listagem.tarefas.model.User;
import listagem.tarefas.services.TaskService;
import listagem.tarefas.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static listagem.tarefas.TestUtils.createDate;
import static listagem.tarefas.TestUtils.generateTask;
import static listagem.tarefas.enums.Status.EM_ANDAMENTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(
        classes = { TestJpaConfig.class },
        loader = AnnotationConfigContextLoader.class
)
@Transactional
@Import({TaskService.class, UserService.class})
public class TaskServicesTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    private User superUser, ownerUser, otherUser;

    @Before
    public void instanciateTestUsers() throws Exception {
        if (superUser == null) {
            Map<String, Object> user = new HashMap<>();
            user.put("username", "superUser");
            user.put("senha", "superUserTestPassword");
            user.put("admin", true);
            superUser = this.userService.save(user);
            user.put("username", "ownerUser");
            user.put("senha", "ownerPassword");
            user.put("admin", false);
            ownerUser = this.userService.save(user);
            user.put("username", "otherUser");
            user.put("senha", "otherPassword");
            otherUser = this.userService.save(user);
        }
    }

    @Test
    public void saveTaskWithStringDateAndStringStatus() throws ParseException {
        Map<String, Object> newTask = generateTask("Tarefa simples", "tarefa com data", "01/01/2024",
                "31/01/2024", "Pendente");
        Task savedTask = this.taskService.save(newTask);
        Date creationDate = createDate(1, 1, 2024);
        Date dueDate = createDate(31, 1, 2024);
        assertEquals(savedTask.getCreatedAt().compareTo(creationDate), 0);
        assertEquals(savedTask.getDueDate().compareTo(dueDate), 0);
        assertEquals(savedTask.getStatus(), Status.PENDENTE);
    }

    @Test
    public void assossiateTaskToUser() throws ParseException {
        Map<String, Object> newTask = generateTask("Tarefa associada", "tarefa associada a um usuario", "01/01/2024",
                "31/01/2024", "Pendente", ownerUser.getId());
        Task savedTask = this.taskService.save(newTask);
        assertEquals(savedTask.getUser(), ownerUser);
    }

    @Test
    public void userCanEditTheirTask() throws ParseException, IllegalAccessException {
        Map<String, Object> newTask = generateTask("Tarefa associada", "tarefa com descrição ruim", "01/01/2024",
                "31/01/2024", "Pendente", ownerUser.getId());
        Task savedTask = this.taskService.save(newTask);
        newTask = generateTask("Tarefa associada", "tarefa com descrição Boa", "01/01/2024",
                "31/01/2024", "emAndamento", ownerUser.getId());
        savedTask = this.taskService.update(savedTask.getId(), newTask, ownerUser.getId());
        assertEquals(savedTask.getUser(), ownerUser);
        assertEquals(savedTask.getDescription(), "tarefa com descrição Boa");
        assertEquals(savedTask.getStatus(), EM_ANDAMENTO);
    }

    @Test(expected = IllegalAccessException.class)
    public void userCantAssociateTaskFromAnotherUser() throws ParseException, IllegalAccessException {
        Map<String, Object> newTask = generateTask("Tarefa associada", "tarefa associada a um usuario", "01/01/2024",
                "31/01/2024", "Pendente", ownerUser.getId());
        Task savedTask = this.taskService.save(newTask);
        newTask = generateTask("Tarefa re-associada", "tarefa associada a outro usuario", "01/01/2024",
                "31/01/2024", "Pendente", otherUser.getId());
        this.taskService.update(savedTask.getId(), newTask, otherUser.getId());
    }

    @Test
    public void deleteTaskUnassociated() throws ParseException, IllegalAccessException {
        Map<String, Object> newTask = generateTask("Tarefa para deletar", "tarefa feita para ser deletada",
                "01/01/2024", "31/01/2024", "Pendente");
        Task savedTask = this.taskService.save(newTask);
        this.taskService.delete(savedTask.getId(), otherUser.getId());
    }

    @Test(expected = IllegalAccessException.class)
    public void deleteTaskFromOtherUser() throws ParseException, IllegalAccessException {
        Map<String, Object> newTask = generateTask("Tarefa não deletável", "tarefa feita para não ser deletada por outro usuario",
                "01/01/2024", "31/01/2024", "Pendente", ownerUser.getId());

        Task savedTask = this.taskService.save(newTask);
        this.taskService.delete(savedTask.getId(), otherUser.getId());
    }

    @Test
    public void userAdminCanManipulateOthersTasks() throws ParseException, IllegalAccessException {
        Map<String, Object> taskToUpdate = generateTask("Tarefa associada", "tarefa associada a um usuario", "01/01/2024",
                "31/01/2024", "Pendente", ownerUser.getId());
        Map<String, Object> taskToDelete = generateTask("Tarefa para deletar", "tarefa para deletar de um usuario", "01/01/2024",
                "31/01/2024", "Pendente", ownerUser.getId());
        Task associatedTask = this.taskService.save(taskToUpdate);
        Task toDeleteTaskTask = this.taskService.save(taskToDelete);
        taskToUpdate = generateTask("Tarefa re-associada", "tarefa associada a um novo usuario, pelo admin", "01/01/2024",
                "31/01/2024", "Pendente", otherUser.getId());
        associatedTask = this.taskService.update(associatedTask.getId(), taskToUpdate, superUser.getId());
        assertEquals(associatedTask.getUser(), otherUser);
        assertEquals(associatedTask.getTitle(), "Tarefa re-associada");
        this.taskService.delete(toDeleteTaskTask.getId(), superUser.getId());
    }
    
}

