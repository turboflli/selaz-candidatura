package listagem.tarefas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe para facilitar criação de objetos de teste
 * */
public class TestUtils {

    public static Map<String, Object> generateUser(String userName, String password, String nivel, boolean admin) {
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("username", userName);
        newUser.put("senha", password);
        newUser.put("nivel", nivel);
        newUser.put("admin", admin);
        return newUser;
    }

    public static JSONObject buildTestUser(String username) {
        JSONObject newUser = new JSONObject();
        try {
            newUser.put("username", username);
            newUser.put("senha", "@P!$#23");
            newUser.put("nivel", "basico");
            newUser.put("admin", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newUser;

    }

    public static JSONObject extractResponseObject(MvcResult result) {
        JSONObject object = null;
        try {
            String apiReturn = result.getResponse().getContentAsString();
            object = new JSONObject(apiReturn);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static JSONArray extractResponseArray(MvcResult result) {
        JSONArray array = null;
        try {
            String apiReturn = result.getResponse().getContentAsString();
            array = new JSONArray(apiReturn);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static Date createDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Map<String, Object> generateTask(String title, String description, String createdAt, String dueDate, String status) {
        Map<String, Object> newTask = new HashMap<>();
        newTask.put("title", title);
        newTask.put("description", description);
        newTask.put("createdAt", createdAt);
        newTask.put("dueDate", dueDate);
        newTask.put("status", status);
        return newTask;
    }

    public static Map<String, Object> generateTask(String title, String description, String createdAt, String dueDate, String status, Integer userId) {
        Map<String, Object> newTask = generateTask(title, description, createdAt, dueDate, status);
        newTask.put("userId", userId);
        return newTask;
    }

    public static JSONObject buildTestTask(String title, String dueDate) {
        JSONObject newtask = new JSONObject();
        try {
            newtask.put("title", title);
            newtask.put("description", "Tarefa de teste");
            newtask.put("createdAt", "01/01/2024");
            newtask.put("dueDate", dueDate);
            newtask.put("status", "Pendente");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newtask;

    }

    public static JSONObject buildTestTask(String title, String dueDate, String status) {
        JSONObject newtask = buildTestTask(title, dueDate);
        try {
            newtask.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newtask;
    }
}
