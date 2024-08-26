package listagem.tarefas.user;

import listagem.tarefas.TestJpaConfig;
import listagem.tarefas.model.User;
import listagem.tarefas.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

import static listagem.tarefas.TestUtils.generateUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(
        classes = { TestJpaConfig.class },
        loader = AnnotationConfigContextLoader.class
)
@Transactional
@Import(UserService.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void encryptPasswordOnSave()  {
        //criar usuários
        Map<String, Object> newUser = generateUser("userAdmin", "@P!$#23", null, true);
        User savedUser = this.userService.save(newUser);
        assertNotEquals(savedUser.getSenha(), "@P!$#23");
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorUpdateUnexistedUser() throws Exception {
        Map<String, Object> newUser = generateUser("userAdmin", "@P!$#23", null, true);
        User savedUser = this.userService.update(0, newUser);
    }

    @Test
    public void deleteUser() throws Exception {
        //criar usuários
        Map<String, Object> newUser = generateUser("userAdmin", "@P!$#23", null, true);
        User savedUser = this.userService.save(newUser);
        this.userService.delete(savedUser.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorDeleteUnexistedUser() throws Exception {
        this.userService.delete(0);
    }

    @Test
    public void loggedIn()  {
        //criar usuários
        Map<String, Object> newUser = generateUser("loggedUser", "@P!$#23", null, true);
        User savedUser = this.userService.save(newUser);
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "loggedUser");
        credentials.put("senha", "@P!$#23");
        User loggedUser = this.userService.findByLogin(credentials);
        assertEquals(savedUser, loggedUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void loggedWithWrongCredentials() throws Exception {
        Map<String, Object> newUser = generateUser("wrongUser", "@P!$#23", null, true);
        this.userService.save(newUser);
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "wrongUser");
        credentials.put("senha", "wrongpassword");
        User loggedUser = this.userService.findByLogin(credentials);
    }
}
