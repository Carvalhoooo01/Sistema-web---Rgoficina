package rg_oficina_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
class RgOficinaBackendApplicationTests {

	public static void main(String[] args) {


        SpringApplication.run(RgOficinaBackendApplicationTests.class, args);

        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");

	}

}
