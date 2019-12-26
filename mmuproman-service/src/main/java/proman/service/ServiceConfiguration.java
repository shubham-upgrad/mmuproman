package proman.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Entity;

@Configuration
@ComponentScan // This is ****ONLY**** for @Controllers,@Component,@Service,@Repository
@EntityScan("proman.service.entity")
public class ServiceConfiguration {
}
