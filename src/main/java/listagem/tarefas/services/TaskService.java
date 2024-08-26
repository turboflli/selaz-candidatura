package listagem.tarefas.services;


import listagem.tarefas.enums.Status;
import listagem.tarefas.model.*;
import listagem.tarefas.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

import static listagem.tarefas.services.JsonConverter.findStatus;


@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private UserRepository userRepository;

    public Task save(Map<String,Object> json) throws ParseException {
        Task newTask = JsonConverter.json_toTask(json);
        associateUser(newTask, json);
        return this.repository.save(newTask);
    }

    //Salva uma tarefa e já associa a um usuário
    public Task save(Map<String,Object> json, Integer userId) throws ParseException {
        Task newTask = JsonConverter.json_toTask(json);
        validateInitialUser(json, userId);
        associateUser(newTask, json);
        return this.repository.save(newTask);
    }

    public Task update(Integer taskId, Map<String,Object> json, Integer userId) throws ParseException, IllegalAccessException {
        Optional<Task> possibleOldTask = this.repository.findById(taskId);
        if (possibleOldTask.isEmpty()) {
            throw new IllegalArgumentException("Tarefa não encontrado com id: "+taskId);
        }
        Task oldTask = possibleOldTask.get();
        this.validateUserPermission(oldTask, userId);
        Task newTask = JsonConverter.json_toTask(json);
        this.updateTaskValues(oldTask, newTask);
        this.associateUser(oldTask, json);
        return this.repository.save(oldTask);
    }

    public void delete(Integer taskId, Integer userId) throws IllegalAccessException {
        Optional<Task> possibleTask = this.repository.findById(taskId);
        if (possibleTask.isEmpty()) {
            throw new IllegalArgumentException("Tarefa não encontrado com id: "+taskId);
        }
        Task task = possibleTask.get();
        this.validateUserPermission(task, userId);
        this.repository.deleteById(taskId.intValue());
    }

    private void updateTaskValues(Task oldTask, Task newTask) {
        oldTask.setTitle(newTask.getTitle());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setDueDate(newTask.getDueDate());
        oldTask.setUser(newTask.getUser());
        oldTask.setStatus(newTask.getStatus());
    }

    private void validateUserPermission(Task task, Integer userId) throws IllegalAccessException {
        if (task.getUser() != null) {
            if (task.getUser().getId().intValue() != userId.intValue()) {
                User user = this.userRepository.findById(userId).get();
                if (!user.isAdmin()) {
                    throw new IllegalAccessException("Seu usuário não pode modificar ou deletar essa Tarefa");
                }
            }
        }
    }

    private void validateInitialUser(Map<String, Object> json, Integer userId) {
        Integer userIdToAssociate = retriveUserId(json);
        if (userIdToAssociate != null){
            User loggedUser = userRepository.findById(userId).get();
            if (!loggedUser.isAdmin() && userId.intValue() != userIdToAssociate.intValue()) {
                throw new IllegalArgumentException("Você não tem permissão de criar uma tarefa para outro usuário");
            }
        }
    }

    private Integer retriveUserId(Map<String, Object> json) {
        Object id = json.get("userId");
        if (id == null){
            return null;
        }
        if (id instanceof Integer){
            return (Integer) id;//Diretamente na api vem como Integer
        } else {
            return Integer.parseInt((String) id);//Pela tela vem como String
        }
    }

    private void associateUser(Task newTask, Map<String, Object> json) {
        Integer userId = retriveUserId(json);
        if (userId != null){
            Optional<User> possibleUser = userRepository.findById(userId);
            if (possibleUser.isEmpty()) {
                throw new IllegalArgumentException("Impossível associar Tarefa a um usuário inexistente. Usuário com id : " + userId + " não encontrado");
            }
            User user = possibleUser.get();
            newTask.setUser(user);
        }
    }

    public List<Task> findByUser(Integer userId, String status, String sort) {
        Optional<User> possibleUser = userRepository.findById(userId);
        if (possibleUser.isEmpty()) {
            throw new IllegalArgumentException("Impossível recuperar Tarefas de um usuário inexistente. Usuário com id : " + userId + " não encontrado");
        }
        User user = possibleUser.get();
        if (status != null) {
            Status statusToFilter = findStatus(status);
            if(sort.equals("dueDate")) {
                return this.repository.findByUserAndStatusOrderedByDate(user, statusToFilter);
            }
            return this.repository.findByUserAndStatus(user, statusToFilter);
        }
        if(sort.equals("dueDate")) {
            return this.repository.findByUserOrderedByDate(user);
        }
        return this.repository.findByUser(user);
    }
}