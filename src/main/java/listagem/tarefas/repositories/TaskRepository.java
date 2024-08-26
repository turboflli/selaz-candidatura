package listagem.tarefas.repositories;

import listagem.tarefas.enums.Status;
import listagem.tarefas.model.Task;
import listagem.tarefas.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query("SELECT t FROM task t WHERE t.user=:user")
    List<Task> findByUser(@Param("user") User user);

    @Query("SELECT t FROM task t WHERE t.user=:user ORDER BY t.dueDate asc")
    List<Task> findByUserOrderedByDate(@Param("user") User user);

    //Encontra tarefas que não tem nenhum usuário atribuido
    @Query("SELECT t FROM task t WHERE t.user=null")
    List<Task> findUnassociatedTasks();

    @Query("SELECT t FROM task t ORDER BY t.dueDate asc")
    List<Task> findOrderByDate();

    @Query("SELECT t FROM task t WHERE t.status=:status")
    List<Task> findByStatus(@Param("status") Status status);

    @Query("SELECT t FROM task t WHERE t.status=:status ORDER BY t.dueDate asc")
    List<Task> findByStatusOrderedByDate(@Param("status") Status status);

    @Query("SELECT t FROM task t WHERE t.user=:user AND t.status=:status")
    List<Task> findByUserAndStatus(@Param("user") User user, @Param("status") Status status);

    @Query("SELECT t FROM task t WHERE t.user=:user AND t.status=:status ORDER BY t.dueDate asc")
    List<Task> findByUserAndStatusOrderedByDate(@Param("user") User user, @Param("status") Status status);

    @Query("DELETE FROM task t WHERE t.id=:id ")
    @Modifying
    void deleteById(@Param("id") int id);




}
