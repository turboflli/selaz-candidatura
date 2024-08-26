package listagem.tarefas.services;



import listagem.tarefas.enums.Status;
import listagem.tarefas.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JsonConverter {

    private static SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");

    public static User json_toUser(Map<String,Object> json) {
        boolean admin = retriveIsAdmin(json);
        return new User((String) json.get("username"),
                (String) json.get("senha"),
                (String) json.get("nivel"),
                admin
        );
    }

    private static boolean retriveIsAdmin(Map<String, Object> json) {
        Object isAdmin = json.get("admin");
        if (isAdmin == null){
            return false;
        }
        if (isAdmin instanceof String){
            return Boolean.parseBoolean((String) isAdmin);//Pela tela vem como String
        } else {
            return (boolean) isAdmin;//Diretamente na api vem como Integer
        }
    }

    public static Task json_toTask(Map<String,Object> json) throws ParseException {
        String dateString = (String) json.get("createdAt");
        Date date = null;
        if (dateString != null) {
            date = parser.parse( dateString);
        } else {
            date = Calendar.getInstance().getTime();
        }
        return new Task((String) json.get("title"),
                (String) json.get("description"),
                date,
                parser.parse((String) json.get("dueDate")),
                findStatus((String) json.get("status"))
        );
    }

    public static Status findStatus(String status) {
        return Arrays.stream(Status.values()).filter( stat -> stat.getDescription().equals(status))
                .findFirst().orElse(null);
    }
}
