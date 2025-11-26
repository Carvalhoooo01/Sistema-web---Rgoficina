package rg_oficina_backend.security;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig
{

    @Bean
    public OpenAPI customOpenAPI()
    {

        return new OpenAPI()
                .info
                        (

                                new Info()
                                        .title("Livraria API")
                                        .version("1.0")
                                        .description("API para gerenciamento de livros e autores\n" +
                                                "Aqui detalhamos os endpoints disponíveis na API, suas funcionalidades" +
                                                " e como utilizá-los.")

                        );

    }

}