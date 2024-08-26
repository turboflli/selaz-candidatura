package listagem.tarefas.services;


import listagem.tarefas.model.User;
import listagem.tarefas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    public static void main(String[] args) {
        String senhaEncriptografada = encryptPassword("@P!$#23");
        System.out.println(senhaEncriptografada);
    }

    @Autowired
    private UserRepository repository;

    public User save(Map<String,Object> json) {
        User newUser = JsonConverter.json_toUser(json);
        String senha = newUser.getSenha();
        senha = this.encryptPassword(senha);
        newUser.setSenha(senha);
        return this.repository.save(newUser);
    }

    public User update(Integer userId, Map<String,Object> json) throws Exception {
        Optional<User> possibleOldUser = this.repository.findById(userId);
        if (possibleOldUser.isEmpty()) {
            throw new IllegalArgumentException("Usuario não encontrado com id: "+userId);
        }
        User oldUser = possibleOldUser.get();
        User newUser = JsonConverter.json_toUser(json);
        String senha = newUser.getSenha();
        senha = this.encryptPassword(senha);
        newUser.setSenha(senha);
        this.updateUserValues(oldUser, newUser);
        return this.repository.save(oldUser);
    }

    public void delete(Integer userId) throws Exception {
        Optional<User> possibleUser = this.repository.findById(userId);
        if (possibleUser.isEmpty()) {
            throw new IllegalArgumentException("Usuario não encontrado com id: "+userId);
        }
        this.repository.deleteById(userId.intValue());
    }

    public User findByLogin(Map<String,Object> json) {
        String username = (String) json.get("username");
        String password = (String) json.get("senha");
        String encryptedPassword = this.encryptPassword(password);
        User findedUser = this.repository.findByLogin(username, encryptedPassword);
        if (findedUser == null) {
            throw new IllegalArgumentException("Credênciais de login inválidas para o usuário");
        }
        return findedUser;
    }

    private void updateUserValues(User oldUser, User newUser) {
        oldUser.setUsername(newUser.getUsername());
        oldUser.setSenha(newUser.getSenha());
        oldUser.setNivel(newUser.getNivel());
        oldUser.setAdmin(newUser.isAdmin());
        oldUser.setTasks(newUser.getTasks());
    }

    private static String encryptPassword(String password) {
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("SHA-256");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder encryptedPassword = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                encryptedPassword.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            return encryptedPassword.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

}
