package listagem.tarefas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket retornaSwagger() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("listagem.tarefas.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(informacoesApi());
    }

    private ApiInfo informacoesApi() {
        return new ApiInfo("Api Básica listagem de tarefas",
                "controla tarefas ligadas a usuários, para monitoramento da conclusão",
                "V1", null,
                null,
                null, null, Collections.emptyList());
    }
}
