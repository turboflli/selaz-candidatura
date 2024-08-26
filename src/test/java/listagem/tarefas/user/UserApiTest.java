package listagem.tarefas.user;

import listagem.tarefas.TestJpaConfig;
import listagem.tarefas.controller.apiBasicController;
import listagem.tarefas.model.User;
import listagem.tarefas.services.UserService;
import org.json.JSONArray;
import org.json.JSONException;
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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static listagem.tarefas.TestUtils.*;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
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
public class UserApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private User superUser;

    @Before
    public void instanciateSuperUser() throws Exception {
        if (superUser == null) {
            Map<String, Object> user = new HashMap<>();
            user.put("username", "superUser");
            user.put("senha", "superUserTestPassword");
            user.put("admin", true);
            superUser = this.userService.save(user);
        }
    }


    private MockHttpSession makeLogin() throws Exception {
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
    public void listUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("[")))
                .andExpect(content().string(endsWith("]")));
    }

    @Test
    public void onSaveRetrivesUser() throws Exception {
        MockHttpSession session = this.makeLogin();

        JSONObject newUser = buildTestUser("userBasic");

        MvcResult result = mockMvc.perform(post("/api/users")
                .session(session)
                .content(newUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject savedUser = extractResponseObject(result);

        assertEquals(savedUser.get("username"),"userBasic");
        assertNotNull(savedUser.get("id"));

    }

    @Test
    public void updateUser() throws Exception {
        MockHttpSession session = this.makeLogin();
        JSONObject newUser = buildTestUser("userToUpdate");

        MvcResult result = mockMvc.perform(post("/api/users")
                .session(session)
                .content(newUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject savedUser = extractResponseObject(result);

        //usuario salvo inicialmente
        assertEquals(savedUser.get("username"),"userToUpdate");
        Integer userId = (Integer) savedUser.get("id");
        assertNotNull(userId);

        //usuario alterado
        savedUser.put("username", "userUpdated");
        result = mockMvc.perform(put("/api/users/{id}", userId)
                .session(session)
                .content(savedUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        savedUser = extractResponseObject(result);
        assertEquals(savedUser.get("username"),"userUpdated");
    }

    @Test
    public void errorOnUpdateWithoutId() throws Exception {

        JSONObject newUser = buildTestUser("userToFail");


        mockMvc.perform(put("/api/users")
                .content(newUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void errorOnUpdateWithUnexistedUser() throws Exception {
        MockHttpSession session = this.makeLogin();
        JSONObject newUser = buildTestUser("userToFail");

        Integer userId =0;
        String errorMessage = mockMvc.perform(put("/api/users/{id}", userId)
                .session(session)
                .content(newUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals(errorMessage,"Usuario não encontrado com id: 0");
    }

    @Test
    public void deleteUser() throws Exception {
        MockHttpSession session = this.makeLogin();
        JSONObject newUser = buildTestUser("userToDelete");

        MvcResult result = mockMvc.perform(post("/api/users")
                .session(session)
                .content(newUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject savedUser = extractResponseObject(result);

        //usuario salvo inicialmente
        assertEquals(savedUser.get("username"),"userToDelete");
        Integer userId = (Integer) savedUser.get("id");
        assertNotNull(userId);

        //usuario deletado
        mockMvc.perform(delete("/api/users/{id}", userId)
                .session(session)
                .content(savedUser.toString()))
                .andExpect(status().isOk());

        result = mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        JSONArray users = extractResponseArray(result);
        //Se retornar algum usuário não pode ser o que foi deletado
        for (int index=0; index <users.length(); index++ ){
            JSONObject user = users.getJSONObject(index);
            assertNotEquals(user.getString("username"), "userToDelete");
        }


    }

    @Test
    public void errorOnDeleteWithoutId() throws Exception {
        mockMvc.perform(delete("/api/users"))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void errorOnDeleteWithUnexistedUser() throws Exception {
        MockHttpSession session = this.makeLogin();
        Integer userId =0;
        String errorMessage = mockMvc.perform(delete("/api/users/{id}", userId).session(session))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals(errorMessage,"Usuario não encontrado com id: 0");
    }

}
