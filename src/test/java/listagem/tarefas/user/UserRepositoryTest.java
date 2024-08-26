package listagem.tarefas.user;

import listagem.tarefas.TestJpaConfig;
import listagem.tarefas.model.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(
        classes = { TestJpaConfig.class },
        loader = AnnotationConfigContextLoader.class
)
@Transactional
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByLogin()  {
        //criar usuários
        User userAdmin = new User("userAdmin", "@P!$#23", null, true);
        userRepository.save(userAdmin);
        User userBasic = new User("userBasic", "teste10", null, false);
        userRepository.save(userBasic);
        //encontrar usuários
        User findedUser = userRepository.findByLogin("userAdmin", "@P!$#23");
        assertEquals(findedUser.isAdmin(), true);
        findedUser = userRepository.findByLogin("userBasic", "teste10");
        assertEquals(findedUser.isAdmin(), false);
    }

    @Test
    public void deleteUser()  {
        //criar usuário
        User userToDelete = new User("userToDelete", "teste10", "médio", false);
        userRepository.save(userToDelete);
        //encontrar usuários
        User findedUser = userRepository.findByLogin("userToDelete", "teste10");
        assertEquals(findedUser.isAdmin(), false);
        assertEquals(findedUser.getNivel(), "médio");
        //deleter usuário
        userRepository.deleteById(findedUser.getId());
        User notFindedUser = userRepository.findByLogin("userToDelete", "teste10");
        assertEquals(notFindedUser, null);
    }

    @Test()
    public void deleteUnexistedUser()  {
        //deleter usuário
        userRepository.deleteById(0);
    }

    @Test
    public void findUserByWrongLogin()  {
        //criar usuários
        User userBasic = new User("userWrong", "notlogged", null, false);
        userRepository.save(userBasic);
        //encontrar usuários
        User findedUser = userRepository.findByLogin("userRight", "logged");
        assertNull(findedUser);
    }

}
