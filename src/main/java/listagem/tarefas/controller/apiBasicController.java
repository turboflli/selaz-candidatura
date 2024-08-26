package listagem.tarefas.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import listagem.tarefas.model.Task;
import listagem.tarefas.model.User;
import listagem.tarefas.repositories.TaskRepository;
import listagem.tarefas.repositories.UserRepository;
import listagem.tarefas.services.ChaveUtils;
import listagem.tarefas.services.TaskService;
import listagem.tarefas.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static listagem.tarefas.services.JsonConverter.findStatus;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/api")
public class apiBasicController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    /**
     * ENDPOINTS DO USUÁRIO
     * */

    @ApiOperation(value = "Endpoint lista Usuários", notes = "Lista todos os usuários")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> listUser() {
        return (List<User>) this.userRepository.findAll();
    }

    @ApiOperation(value = "Endpoint Salvar Usuário", notes = "Salva um novo usuário. É necessário ser um adminitrador para usar essa função")
    @RequestMapping(value="/users", method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User saveUser(@ApiParam(name = "user", example = "{'username': 'login', 'senha': 'password', 'nivel': <String>, 'admin': false}", value = "Json contendo usuário para ser criado")
                             @RequestBody  Map<String,Object> json, HttpServletRequest request) throws IllegalAccessException {
        this.blockIfLoggedUserIsntAdmin(request);
        User savedUser;
        try{
            savedUser = this.userService.save(json);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return savedUser;
    }

    @ApiOperation(value = "Endpoint Atualizar Usuário", notes = "Atualiza um usuário já existente. É necessário ser um adminitrador para usar essa função")
    @RequestMapping(value="/users/{id}", method=RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User updateUser(@ApiParam(name = "user", example = "{'username': 'login', 'senha': 'password', 'nivel': <String>, 'admin': false}", value = "Json contendo usuário para ser Atualizado, note que não deve passar o id nesse parametro")
                               @RequestBody  Map<String,Object> json,
                           @ApiParam(name = "id", example = "2", value = "Id do usuário a ser atualizado") @PathVariable(value = "id") Integer userId, HttpServletRequest request) throws Exception{
        this.blockIfLoggedUserIsntAdmin(request);
        User savedUser;
        try{
            savedUser = this.userService.update(userId, json);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return savedUser;
    }

    @ApiOperation(value = "Endpoint deletar Usuário", notes = "Deleta um usuário já existente. É necessário ser um adminitrador para usar essa função")
    @RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@ApiParam(name = "id", example = "2", value = "Id do usuário a ser deletado") @PathVariable(value = "id") Integer userId, HttpServletRequest request) throws Exception {
        this.blockIfLoggedUserIsntAdmin(request);
        try{
            this.userService.delete(userId);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @ApiOperation(value = "Endpoint Realizar login", notes="Realiza seu login no sistema, para ter acesso as funções")
    @RequestMapping(value="/login", method=RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String login(@ApiParam(name = "credentials", example = "{'username': 'login', 'senha': 'password'}", value = "Json contendo username e senha do usuário") @RequestBody Map<String,Object> json, HttpServletRequest request){
        try{
            User findedUser = this.userService.findByLogin(json);
            byte[] data = SerializationUtils.serialize(findedUser.getId());
            request.getSession().setAttribute("userId", data);

        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return "logged in";
    }

    @ApiOperation(value = "Endpoint Realizar logout", notes="Realiza seu logout no sistema")
    @RequestMapping(value="/logout", method=RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String logout(HttpServletRequest request){
        try{
            request.getSession().removeAttribute("userId");

        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return "logged out";
    }

    /**
     * ENDPOINTS DAS TAREFAS
     * */

    @ApiOperation(value = "Endpoint Listar Tarefas", notes="Lista as tarefas, se não passar filtro de status, lista do usuário logado, do contrário de todos os usuários")
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasks(@ApiParam(name = "status", example = "Pendente, emAndamento ou Concluida", value = "Status desejado para busca", required = false) @RequestParam(required = false) String status,
                                @ApiParam(name = "sort", example = "dueDate", value = "Atributo para ordenar as tarefas, seja do usuário logado ou de todos, atualmente só possue dueDate", required = false) @RequestParam(required = false, defaultValue  = "") String sort, HttpServletRequest request) throws IllegalAccessException {
        if (status == null) {
            Integer userId = this.recoveryUserId(request);
            User loggedUser = this.userRepository.findById(userId).get();
            if (sort.equals("dueDate")) {
                return (List<Task>) this.taskRepository.findByUserOrderedByDate(loggedUser);
            }
            return (List<Task>) this.taskRepository.findByUser(loggedUser);
        }
        if (sort.equals("dueDate")) {
            return (List<Task>) this.taskRepository.findByStatusOrderedByDate(findStatus(status));
        }
        return (List<Task>) this.taskRepository.findByStatus(findStatus(status));
    }

    @ApiOperation(value = "Endpoint Criar Tarefa", notes = "Cria uma tarefa, lembre-se que não pode atribuir uma tarefa a outro usuário, a menos que seja um administrador.")
    @RequestMapping(value="/tasks", method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Task saveTask(@ApiParam(name = "task", example = "{'title': 'tarefa 1', 'description': 'Tarefa a ser realizada da forma....', 'createdAt': '01/02/2011', 'dueDate': '12/02/2012', 'userId': 2, 'status': 'Pendente'}", value = "Json contendo Tarefa para ser criada. Pode omitir a data de criação (createdAt) para usar tempo do sistema")
            @RequestBody  Map<String,Object> json, HttpServletRequest request) throws ParseException, IllegalAccessException {
        Integer userId = this.recoveryUserId(request);
        Task savedTask;
        try{
            savedTask = this.taskService.save(json, userId);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return savedTask;
    }

    @ApiOperation(value = "Endpoint Atualizar Tarefa", notes = "Atualiza uma tarefa, lembre-se que não pode atualizar uma tarefa de outro usuário, a menos que seja um administrador.")
    @RequestMapping(value="/tasks/{id}", method=RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Task updateTask(@ApiParam(name = "task", example = "{'title': 'tarefa 1', 'description': 'Taarefa a ser realizada da forma....', 'dueDate': '12/02/2012', 'userId': 2, 'status': 'emAndamento'}", value = "Json contendo Tarefa para ser Atualizada. Note que a data de criação não será atualizada, e que não deve passar o id nesse parametro") @RequestBody  Map<String,Object> json,
                           @ApiParam(name = "id", example = "112", value = "id da Tarefa para ser atualizada") @PathVariable(value = "id") Integer taskId, HttpServletRequest request) throws Exception{
        Integer userId = this.recoveryUserId(request);
        Task savedTask;
        try{
            savedTask = this.taskService.update(taskId, json, userId);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return savedTask;
    }

    @ApiOperation(value = "Endpoint Deletar Tarefa", notes = "Deleta uma tarefa, lembre-se que não pode Deletar uma tarefa de outro usuário, a menos que seja um administrador.")
    @RequestMapping(value="/tasks/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public void deleteTask(@ApiParam(name = "id", example = "112", value = "id da Tarefa para ser deletada") @PathVariable(value = "id") Integer taskId, HttpServletRequest request) throws Exception {
        Integer userId = this.recoveryUserId(request);
        try{
            this.taskService.delete(taskId, userId);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @ApiOperation(value = "Endpoint Listar Tarefas de um usuario", notes = "Lista as tarefas de um usuário. Note que pode listar tarefa de qualquer usuário")
    @RequestMapping(value = "/users/{userId}/tasks", method = RequestMethod.GET)
    @ResponseBody
    public List<Task> listTasksFromUser(@ApiParam(name = "status", example = "Pendente, emAndamento ou Concluida", value = "Status desejado para busca", required = false) @RequestParam(required = false) String status,
                            @ApiParam(name = "sort", example = "dueDate", value = "Atributo para ordenar as tarefas, atualmente só possue dueDate", required = false) @RequestParam(required = false, defaultValue  = "") String sort,
                            @ApiParam(name = "userId", example = "2", value = "Id do usuário para consulta de tarefas") @PathVariable(value = "userId") Integer userId, HttpServletRequest request) throws IllegalAccessException {
        return (List<Task>) this.taskService.findByUser(userId, status, sort);
    }

    /**
     * ENDPOINTS COM SEGURANÇA PARA USO NA TELA, SEMELHANTES AOS ANTERIORES POREM CRIPTOGRAMFAM VALORES RECEBIDOS, AFIM DE QUE NÃO POSSAM SER OBSERVADOS NA REDE
     * */

    @ApiIgnore
    @RequestMapping(value="/loginSafe", method=RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String loginSafe( @RequestBody Map<String,Object> mapa, HttpServletRequest request){
        try{
            JSONObject json = extractSafeObject((String) mapa.get("credentials"), request);
            User findedUser = this.userService.findByLogin(json.toMap());
            byte[] data = SerializationUtils.serialize(findedUser.getId());
            request.getSession().setAttribute("userId", data);

        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return "logged in";
    }

    private JSONObject extractSafeObject(String jsonStringed, HttpServletRequest request) {
        byte[] data = (byte[]) request.getSession().getAttribute("chave");
        String chave = data != null ? (String) SerializationUtils.deserialize(data) : null;
        String iv = (String) request.getSession().getAttribute("iv");
        jsonStringed = ChaveUtils.descriptografar(jsonStringed, chave, iv);
        JSONObject json = new JSONObject(jsonStringed);
        return json;
    }

    @ApiIgnore
    @RequestMapping(value="/usersSafe", method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveUserSafe(@RequestBody  Map<String,Object> mapa, HttpServletRequest request) throws IllegalAccessException {
        this.blockIfLoggedUserIsntAdmin(request);
        JSONObject json = extractSafeObject((String) mapa.get("user"), request);
        try{
            this.userService.save(json.toMap());
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return "Usuário salvo com sucesso";
    }

    @ApiIgnore
    @RequestMapping(value="/usersSafe/{id}", method=RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateUserSafe(@RequestBody  Map<String,Object> mapa, @PathVariable(value = "id") Integer userId, HttpServletRequest request) throws Exception {
        this.blockIfLoggedUserIsntAdmin(request);
        JSONObject json = extractSafeObject((String) mapa.get("user"), request);
        try{
            this.userService.update(userId, json.toMap());
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return "Usuário atualizado com sucesso";
    }

    /**
     * ExceptionHandler
     * */

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR,
            reason="Argumento invalido")
    @ExceptionHandler({IllegalArgumentException.class})
    public String notFound(Exception e) {
        return e.getMessage();
    }

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR,
            reason="Argumento invalido")
    @ExceptionHandler({IllegalAccessException.class})
    public String notAccess(Exception e) {
        return e.getMessage();
    }

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR,
            reason="Valor Incorreto")
    @ExceptionHandler({ParseException.class})
    public String wrongValue(Exception e) {
        return "Valor incorreto informado, para mias informaçoes: " + e.getMessage();
    }

    /**
     * Acess check methods
     * */
    private Integer recoveryUserId(HttpServletRequest request) throws IllegalAccessException {
        byte[] data = (byte[]) request.getSession().getAttribute("userId");
        Integer userId = data != null ? (Integer) SerializationUtils.deserialize(data) : null;
        if (userId == null) {
            throw new IllegalAccessException("Usuário não logado, por favor faça login.");
        }
        return userId;
    }

    private boolean isLoggedUserAdmin(HttpServletRequest request)  throws IllegalAccessException {
        byte[] data = (byte[]) request.getSession().getAttribute("userId");
        Integer userId = data != null ? (Integer) SerializationUtils.deserialize(data) : null;
        if (userId == null) {
            throw new IllegalAccessException("Usuário não logado, por favor faça login.");
        }
        return this.userRepository.findById(userId).get().isAdmin();
    }

    private void blockIfLoggedUserIsntAdmin(HttpServletRequest request) throws IllegalAccessException {
        if (!this.isLoggedUserAdmin(request)){
            throw new IllegalAccessException("Apenas usuário administrador pode acessar essa função");
        }
    }


}