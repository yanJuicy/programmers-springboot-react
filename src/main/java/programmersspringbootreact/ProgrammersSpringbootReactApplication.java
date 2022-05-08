package programmersspringbootreact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProgrammersSpringbootReactApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgrammersSpringbootReactApplication.class, args);
    }

}
