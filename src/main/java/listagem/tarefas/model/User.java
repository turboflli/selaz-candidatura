package listagem.tarefas.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name="user_pessoa")
@Table(name = "user_pessoa")
public class User  implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "username")
    private String username;
    @Column(name = "senha")
    private String senha;
    @Column(name = "nivel")
    private String nivel;
    @Column(name = "admin")
    private boolean admin;
    @OneToMany(fetch = FetchType.EAGER, mappedBy="user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User() {
    }

    public User(String username, String senha, String nivel, boolean admin) {
        this.username = username;
        this.senha = senha;
        this.nivel = nivel;
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (Id != user.Id) return false;
        if (admin != user.admin) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (senha != null ? !senha.equals(user.senha) : user.senha != null) return false;
        if (nivel != null ? !nivel.equals(user.nivel) : user.nivel != null) return false;
        return tasks != null ? tasks.equals(user.tasks) : user.tasks == null;
    }

}
