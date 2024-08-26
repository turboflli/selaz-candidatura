package listagem.tarefas.task;

import listagem.tarefas.TestJpaConfig;
import listagem.tarefas.controller.apiBasicController;
import listagem.tarefas.model.User;
import listagem.tarefas.repositories.TaskRepository;
import listagem.tarefas.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static listagem.tarefas.TestUtils.*;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = { TestJpaConfig.class },
        loader = AnnotationConfigWebContextLoader.class
)
@WebMvcTest(apiBasicController.class)
@AutoConfigureMockMvc
@Import(apiBasicController.class)
@ComponentScan(basePackages = {"listagem.tarefas"})
@Transactional
public class TaskApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskRepository taskRepository;
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

    private MockHttpSession makeUserLogin() throws Exception {
        JSONObject credentials = new JSONObject ();
        credentials.put("username", "ownerUser");
        credentials.put("senha", "ownerPassword");
        MockHttpSession session = (MockHttpSession) mockMvc.perform(post("/api/login")
                .content(credentials.toString())
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getRequest().getSession();

        return session;
    }

    private MockHttpSession makeSuperUserLogin() throws Exception {
        JSONObject  credentials = new JSONObject ();
        credentials.put("username", "superUser");
        credentials.put("senha", "superUserTestPassword");
        MockHttpSession session = (MockHttpSession) mockMvc.perform(post("/api/login")
                .content(credentials.toString())
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getRequest().getSession();

        return session;
    }

    @Test
    public void listTask() throws Exception {
        MockHttpSession session = makeUserLogin();
        mockMvc.perform(get("/api/tasks").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("[")))
                .andExpect(content().string(endsWith("]")));
    }

    @Test
    public void associateUserOnCreation() throws Exception {
        MockHttpSession session = makeUserLogin();
        JSONObject newTask = buildTestTask("test task", "12/01/2024");
        newTask.put("userId", ownerUser.getId());
        MvcResult result = mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject savedTask = extractResponseObject(result);
        assertEquals(savedTask.get("title"),"test task");
        String userName = savedTask.getString("userUsername");
        assertEquals(userName, ownerUser.getUsername());
        assertNotNull(savedTask.get("id"));
    }

    @Test
    public void cantAssociateAnotherUserOnCreation() throws Exception {
        MockHttpSession session = makeUserLogin();
        JSONObject newTask = buildTestTask("test task", "12/01/2024");
        newTask.put("userId", otherUser.getId());
        String errorMessage = mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals(errorMessage, "Você não tem permissão de criar uma tarefa para outro usuário");
    }

    @Test
    public void updateMyTask() throws Exception {
        //criar tarefas
        MockHttpSession session = makeUserLogin();
        JSONObject newTask = buildTestTask("test to Update", "12/01/2024");
        newTask.put("userId", ownerUser.getId());
        MvcResult result = mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject savedTask = extractResponseObject(result);
        Integer taskId = savedTask.getInt(("id"));
        assertNotNull(taskId);
        //atualizar tarefa
        newTask.put("title", "task Updated");
        newTask.put("status", "emAndamento");
        result = mockMvc.perform(put("/api/tasks/{id}", taskId)
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        savedTask = extractResponseObject(result);
        assertEquals(savedTask.get("title"), "task Updated");
        assertEquals(savedTask.get("statusDescription"), "emAndamento");

    }

    @Test
    public void cantUpdateAnotherUserTask() throws Exception {
        //criar tarefa para outro usuario
        MockHttpSession session = makeSuperUserLogin();
        JSONObject newTask = buildTestTask("test from another user", "12/01/2024");
        newTask.put("userId", otherUser.getId());
        MvcResult result = mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject savedTask = extractResponseObject(result);
        Integer taskId = savedTask.getInt(("id"));
        assertNotNull(taskId);
        newTask.put("title", "task Updated");
        newTask.put("status", "emAndamento");
        //login com outro usuário e atualizar tarefa
        session = makeUserLogin();
        String errorMessage = mockMvc.perform(put("/api/tasks/{id}", taskId)
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals(errorMessage, "Seu usuário não pode modificar ou deletar essa Tarefa");
    }

    @Test
    public void deleteMyTask() throws Exception {
        //criar tarefa
        MockHttpSession session = makeUserLogin();
        JSONObject newTask = buildTestTask("test to Delete", "31/01/2024");
        newTask.put("userId", ownerUser.getId());
        MvcResult result = mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject savedTask = extractResponseObject(result);
        Integer taskId = savedTask.getInt(("id"));
        assertNotNull(taskId);
        //deletar tarefa
        mockMvc.perform(delete("/api/tasks/{id}", taskId)
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void adminUserCanEditOthersTask() throws Exception {
        //usando login de administrador
        //criar tarefa
        MockHttpSession session = makeSuperUserLogin();
        JSONObject newTask = buildTestTask("test to another user, by admin", "28/01/2024");
        newTask.put("userId", ownerUser.getId());
        MvcResult result = mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject savedTask = extractResponseObject(result);
        Integer taskId = savedTask.getInt(("id"));
        assertNotNull(taskId);
        //atualizar tarefa
        newTask.put("title", "task Updated");
        newTask.put("status", "emAndamento");
        result = mockMvc.perform(put("/api/tasks/{id}", taskId)
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        savedTask = extractResponseObject(result);
        assertEquals(savedTask.get("title"), "task Updated");
        assertEquals(savedTask.get("statusDescription"), "emAndamento");
        //deletar tarefa
        mockMvc.perform(delete("/api/tasks/{id}", taskId)
                .session(session)
                .content(newTask.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void getTasksOrderedByDate() throws Exception {
        //limpar tarefas
        this.taskRepository.deleteAll();
        //criar tarefas
        MockHttpSession session = makeUserLogin();
        JSONObject taskDate1 = buildTestTask("test date 1", "28/01/2024");
        JSONObject taskDate2 = buildTestTask("test date 2", "02/02/2024");
        JSONObject taskDate3 = buildTestTask("test date 3", "09/02/2024");
        taskDate1.put("userId", ownerUser.getId());
        taskDate2.put("userId", ownerUser.getId());
        taskDate3.put("userId", ownerUser.getId());
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(taskDate2.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(taskDate3.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(taskDate1.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //recuperar tarefas ordenadas
        MvcResult result = mockMvc.perform(get("/api/tasks?sort=dueDate")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONArray orderedTasks = extractResponseArray(result);
        assertEquals(orderedTasks.length(), 3);
        String date1 = (String) orderedTasks.getJSONObject(0).get("dueDateFormated");
        String date2 = (String) orderedTasks.getJSONObject(1).get("dueDateFormated");
        String date3 = (String) orderedTasks.getJSONObject(2).get("dueDateFormated");
        assertEquals(date1, "28/01/2024");
        assertEquals(date2, "02/02/2024");
        assertEquals(date3, "09/02/2024");
    }

    @Test
    public void filterByStatus() throws Exception {
        //criar as tarefas
        MockHttpSession session = makeUserLogin();
        JSONObject doneTask1 = buildTestTask("done task 1", "21/01/2024", "Concluida");
        JSONObject doneTask2 = buildTestTask("done task 2", "22/01/2024", "Concluida");
        JSONObject doneTask3 = buildTestTask("done task 3", "23/01/2024", "Concluida");
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(doneTask3.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(doneTask1.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(doneTask2.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        //recuperar as tarefas
        MvcResult result = mockMvc.perform(get("/api/tasks?status={status}&sort=dueDate", "Concluida")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONArray doneTasks = extractResponseArray(result);
        assertEquals(doneTasks.length(), 3);
        JSONObject orderedTask1 = doneTasks.getJSONObject(0);
        assertEquals(orderedTask1.getString("statusDescription"), "Concluida");
        assertEquals(orderedTask1.getString("dueDateFormated"), "21/01/2024");
        JSONObject orderedTask2 = doneTasks.getJSONObject(1);
        assertEquals(orderedTask2.getString("statusDescription"), "Concluida");
        assertEquals(orderedTask2.getString("dueDateFormated"), "22/01/2024");
        JSONObject orderedTask3 = doneTasks.getJSONObject(2);
        assertEquals(orderedTask3.getString("statusDescription"), "Concluida");
        assertEquals(orderedTask3.getString("dueDateFormated"), "23/01/2024");

    }

    @Test
    public void findTaskFromSpecificUserByStatusAndOrdered() throws Exception {
        //criar as tarefas
        MockHttpSession session = makeSuperUserLogin();
        JSONObject doneTask1 = buildTestTask("done task 1", "13/01/2024", "Concluida");
        JSONObject doneTask2 = buildTestTask("done task 2", "28/01/2024", "Concluida");
        JSONObject doneTask3 = buildTestTask("done task 3", "07/02/2024", "Concluida");
        doneTask1.put("userId", otherUser.getId());
        doneTask2.put("userId", otherUser.getId());
        doneTask3.put("userId", otherUser.getId());
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(doneTask3.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(doneTask1.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/tasks")
                .session(session)
                .content(doneTask2.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        //recuperar as tarefas
        MvcResult result = mockMvc.perform(get("/api/users/{userId}/tasks?status={status}&sort=dueDate", otherUser.getId(), "Concluida")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONArray doneTasks = extractResponseArray(result);
        assertEquals(doneTasks.length(), 3);
        JSONObject orderedTask1 = doneTasks.getJSONObject(0);
        assertEquals(orderedTask1.getString("statusDescription"), "Concluida");
        assertEquals(orderedTask1.getString("dueDateFormated"), "13/01/2024");
        assertEquals(orderedTask1.getString("userUsername"), "otherUser");
        JSONObject orderedTask2 = doneTasks.getJSONObject(1);
        assertEquals(orderedTask2.getString("statusDescription"), "Concluida");
        assertEquals(orderedTask2.getString("dueDateFormated"), "28/01/2024");
        assertEquals(orderedTask2.getString("userUsername"), "otherUser");
        JSONObject orderedTask3 = doneTasks.getJSONObject(2);
        assertEquals(orderedTask3.getString("statusDescription"), "Concluida");
        assertEquals(orderedTask3.getString("dueDateFormated"), "07/02/2024");
        assertEquals(orderedTask3.getString("userUsername"), "otherUser");

    }
    
}
