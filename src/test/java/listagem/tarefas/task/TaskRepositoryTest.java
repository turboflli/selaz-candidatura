package listagem.tarefas.task;

import listagem.tarefas.TestJpaConfig;
import listagem.tarefas.enums.Status;
import listagem.tarefas.model.Task;
import listagem.tarefas.model.User;
import listagem.tarefas.repositories.TaskRepository;
import listagem.tarefas.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static listagem.tarefas.TestUtils.createDate;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(
        classes = { TestJpaConfig.class },
        loader = AnnotationConfigContextLoader.class
)
@Transactional
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUser(){
        //criar usuarios
        User user1 = new User("userNumber1", "teste10", null, false);
        User user2 = new User("userNumber2", "teste10", null, false);
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        //criar tarefas
        Task task1 = new Task("Task number 1", "Tarefa de teste numero 1", null, null, Status.PENDENTE);
        Task task2 = new Task("Task number 2", "Tarefa de teste numero 2", null, null, Status.PENDENTE);
        Task task3 = new Task("Task number 3", "Tarefa de teste numero 3", null, null, Status.PENDENTE);
        task1.setUser(user1);
        task2.setUser(user2);
        this.taskRepository.save(task1);
        task2 = this.taskRepository.save(task2);
        this.taskRepository.save(task3);

        //recuperar tarefa
        List<Task> user2Tasks = this.taskRepository.findByUser(user2);
        assertEquals(user2Tasks.size(), 1);
        Task onlyTask = user2Tasks.get(0);
        assertEquals(onlyTask, task2);

        List<Task> nosuerTasks = this.taskRepository.findUnassociatedTasks();
        assertEquals(nosuerTasks.size(), 1);
        onlyTask = nosuerTasks.get(0);
        assertEquals(onlyTask, task3);
    }

    @Test
    public void findByStatus(){
        //criar tarefas
        Task taskDone = new Task("Task Done", "Tarefa de teste completa", null, null, Status.CONCLUIDA);
        Task taskWork1 = new Task("Task working 1", "Tarefa de teste em progresso 1", null, null, Status.EM_ANDAMENTO);
        Task taskWork2 = new Task("Task working 2", "Tarefa de teste em progresso 1", null, null, Status.EM_ANDAMENTO);
        this.taskRepository.save(taskDone);
        taskWork1 = this.taskRepository.save(taskWork1);
        taskWork2 = this.taskRepository.save(taskWork2);

        //recuperar tarefa
        List<Task> workingTasks = this.taskRepository.findByStatus(Status.EM_ANDAMENTO);
        assertEquals(workingTasks.size(), 2);
        assertTrue(workingTasks.contains(taskWork1));
        assertTrue(workingTasks.contains(taskWork2));
        assertFalse(workingTasks.contains(taskDone));

    }

    @Test
    public void findOrderByDate(){
        //criar tarefas
        Date date1 = createDate(1, 3, 2024);
        Date date2 = createDate(15, 3, 2024);
        Date date3 = createDate(1, 4, 2024);
        Task task1 = new Task("Task dia 1", "Tarefa de teste dia 1", null, date1, Status.PENDENTE);
        Task task2 = new Task("Task dia 2", "Tarefa de teste dia 2", null, date2, Status.PENDENTE);
        Task task3 = new Task("Task dia 3", "Tarefa de teste dia 3", null, date3, Status.PENDENTE);
        //limpar banco para garantir que não tenha dados de outros testes
        this.taskRepository.deleteAll();
        task3 = this.taskRepository.save(task3);
        task1 = this.taskRepository.save(task1);
        task2 = this.taskRepository.save(task2);


        //recuperar tarefa
        List<Task> orderedTasks = this.taskRepository.findOrderByDate();
        assertEquals(orderedTasks.get(0), task1);
        assertEquals(orderedTasks.get(1), task2);
        assertEquals(orderedTasks.get(2), task3);

    }

    @Test
    public void deleteTask(){
        //criar usuarios
        User user = new User("userWithouTask", "teste10", null, false);
        user = userRepository.save(user);

        //criar tarefas
        Task task = new Task("Task to delete", "Tarefa de teste para ser deletada", null, null, Status.EM_ANDAMENTO);
        task.setUser(user);
        task = this.taskRepository.save(task);

        //recuperar tarefa
        List<Task> userTasks = this.taskRepository.findByUser(user);
        assertEquals(userTasks.size(), 1);
        Task onlyTask = userTasks.get(0);
        assertEquals(onlyTask, task);
        //tarefa deletada
        this.taskRepository.deleteById(onlyTask.getId());
        userTasks = this.taskRepository.findByUser(user);
        assertEquals(userTasks.size(), 0);
    }

    @Test()
    public void deleteUnexistedTask()  {
        //deleter tarefa
        taskRepository.deleteById(0);
    }

}
