package listagem.tarefas.controller;


import listagem.tarefas.enums.Status;
import listagem.tarefas.model.Task;
import listagem.tarefas.model.User;
import listagem.tarefas.repositories.TaskRepository;
import listagem.tarefas.repositories.UserRepository;
import listagem.tarefas.services.ChaveUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "")
@ApiIgnore
public class JspController {

    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    private String start(Model model, HttpServletRequest request) {
        byte[] data = (byte[]) request.getSession().getAttribute("userId");
        Integer userId = data != null ? (Integer) SerializationUtils.deserialize(data) : null;
        model.addAttribute("hasUser", (userId != null));
        if (userId != null) {
            User loggedUser = this.userRepository.findById(userId).get();
            model.addAttribute("user", loggedUser);
            this.fillTasks(model, loggedUser);
            if (loggedUser.isAdmin()) {
                model.addAttribute("avaliableusers", (List<User>) this.userRepository.findAll());
            }
        } else {
            setSecurityKeys(model, request);
        }
        return "index";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    private String newUser(Model model, HttpServletRequest request) {
        setSecurityKeys(model, request);
        return "main/newUser";
    }

    @RequestMapping(value = "/newTask", method = RequestMethod.GET)
    private String newTask(Model model, HttpServletRequest request) throws IllegalAccessException {
        Integer userId = this.recoveryUserId(request);
        User loggedUser = this.userRepository.findById(userId).get();
        if (loggedUser.isAdmin()) {
            model.addAttribute("avaliableusers", (List<User>) this.userRepository.findAll());
        } else {
            model.addAttribute("avaliableusers", Arrays.asList(loggedUser));
        }
        return "main/newTask";
    }

    @RequestMapping(value = "/editTask/{id}", method = RequestMethod.GET)
    private String editTask(@PathVariable(value = "id") Integer taskId, Model model, HttpServletRequest request) throws IllegalAccessException {
        Integer userId = this.recoveryUserId(request);
        User loggedUser = this.userRepository.findById(userId).get();
        if (loggedUser.isAdmin()) {
            model.addAttribute("avaliableusers", (List<User>) this.userRepository.findAll());
        } else {
            model.addAttribute("avaliableusers", Arrays.asList(loggedUser));
        }
        Task task = this.taskRepository.findById(taskId).get();
        String dateFormated = formatter.format(task.getDueDate());
        model.addAttribute("dateFormated", dateFormated);
        model.addAttribute("task", task);
        return "main/editTask";
    }

    @RequestMapping(value = "/editUser/{id}", method = RequestMethod.GET)
    private String editUser(@PathVariable(value = "id") Integer userIdToUpdate, Model model, HttpServletRequest request) throws IllegalAccessException {
        Integer userId = this.recoveryUserId(request);
        User loggedUser = this.userRepository.findById(userId).get();
        if (!loggedUser.isAdmin()) {
            throw new IllegalAccessException("Apenas administradores tem acesso a essa pagina");
        }
        setSecurityKeys(model, request);
        User user = this.userRepository.findById(userIdToUpdate).get();
        model.addAttribute("user", user);
        return "main/editUser";
    }

    //Método para adicionar na tela as chaves que são usadas para encriptografar os dados
    private void setSecurityKeys(Model model, HttpServletRequest request) {
        String chave = ChaveUtils.gerarChave();
        model.addAttribute("chave", chave);
        byte[] data = SerializationUtils.serialize(chave);
        request.getSession().setAttribute("chave", data);
        String iv = ChaveUtils.gerarIv();
        model.addAttribute("iv", iv);
        request.getSession().setAttribute("iv", iv);
    }

    private Integer recoveryUserId(HttpServletRequest request) throws IllegalAccessException {
        byte[] data = (byte[]) request.getSession().getAttribute("userId");
        Integer userId = data != null ? (Integer) SerializationUtils.deserialize(data) : null;
        if (userId == null) {
            throw new IllegalAccessException("Usuário não logado, por favor faça login.");
        }
        return userId;
    }

    //Preenche as tarefas para apresentação em tela
    private void fillTasks(Model model, User loggedUser) {
        //Tarefas genaris
        List<Task> openTasks = this.taskRepository.findByStatusOrderedByDate(Status.PENDENTE);
        List<Task> workingTasks = this.taskRepository.findByStatusOrderedByDate(Status.EM_ANDAMENTO);
        List<Task> doneTasks = this.taskRepository.findByStatusOrderedByDate(Status.CONCLUIDA);
        model.addAttribute("totalpendente", openTasks.size());
        model.addAttribute("totalemandamento", workingTasks.size());
        model.addAttribute("totalconcluida", doneTasks.size());
        model.addAttribute("opentasks", openTasks);
        model.addAttribute("workingtasks", workingTasks);
        model.addAttribute("donetasks", doneTasks);
        //Minhas tarefas
        List<Task> userTasks = this.taskRepository.findByUserOrderedByDate(loggedUser);
        model.addAttribute("mytasks", userTasks);
    }

}
