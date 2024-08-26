package listagem.tarefas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import listagem.tarefas.enums.Status;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name="task")
@Table(name = "task")
public class Task  implements Serializable {
    private static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "createdat")
    private Date createdAt;
    @Column(name = "duedate")
    private Date dueDate;
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
    @Column(name = "status")
    private Status status;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Transactional
    public String getCreatedAtFormated() {
        return createdAt!= null ? formatter.format(this.createdAt) : "";
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Transactional
    public String getDueDateFormated() {
        return dueDate!= null ? formatter.format(this.dueDate) : "";
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Transactional
    public String getStatusDescription() {
        return this.status.getDescription();
    }

    @Transactional
    public String getUserUsername() {
        return this.user != null ? this.user.getUsername() : "";
    }

    public Task(){}

    public Task( String title, String description, Date createdAt, Date dueDate, Status status) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (Id != task.Id) return false;
        if (title != null ? !title.equals(task.title) : task.title != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        if (createdAt != null ? !createdAt.equals(task.createdAt) : task.createdAt != null) return false;
        if (dueDate != null ? !dueDate.equals(task.dueDate) : task.dueDate != null) return false;
        if (user != null ? !user.equals(task.user) : task.user != null) return false;
        return status != null ? status.equals(task.status) : task.status == null;
    }

}
