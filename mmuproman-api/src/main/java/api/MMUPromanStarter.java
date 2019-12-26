package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import proman.service.ServiceConfiguration;

@SpringBootApplication
@Import({ServiceConfiguration.class})
public class MMUPromanStarter {
    public static void main(String...args){
        SpringApplication.run(MMUPromanStarter.class,args);
    }
}
