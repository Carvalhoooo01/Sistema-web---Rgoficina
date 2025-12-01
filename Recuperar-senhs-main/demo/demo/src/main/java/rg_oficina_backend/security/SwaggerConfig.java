package rg_oficina_backend.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger (SpringDoc OpenAPI).
 * Gera a documentação visual da API, acessível em /swagger-ui.html
 * @author Gustavo Carvalho
 */
@Configuration
public class SwaggerConfig
{
    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("SCOS API - Oficina RG") // Título correto
                                .version("1.0")
                                .description("API para gerenciamento de Ordens de Serviço e Clientes.\n" +
                                        "Aqui detalhamos os endpoints disponíveis na API, suas funcionalidades" +
                                        " e como utilizá-los.")
                );
    }
}