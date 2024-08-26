package listagem.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component
public class GetPort implements ApplicationRunner{
	@Autowired
    private Environment environment;
	public static int port;
	public static String path;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        path=environment.getProperty("server.servlet.context-path");
    	try {
    		port=Integer.parseInt(environment.getProperty("local.server.port"));
    	}catch(java.lang.NumberFormatException e) {
    		port=8080;
    	}
    }
    
}
