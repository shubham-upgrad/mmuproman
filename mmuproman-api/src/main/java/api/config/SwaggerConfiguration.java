package api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
     @Bean
     public Docket swaggerDocket(){
         return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors
                 .basePackage("api.controllers")).paths(PathSelectors.any()).build();
     }

}
