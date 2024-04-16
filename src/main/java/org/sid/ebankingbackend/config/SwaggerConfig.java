 package org.sid.ebankingbackend.config;


 import io.swagger.v3.oas.models.Components;
 import io.swagger.v3.oas.models.OpenAPI;
 import io.swagger.v3.oas.models.info.Contact;
 import io.swagger.v3.oas.models.info.Info;
 import io.swagger.v3.oas.models.info.License;
 import io.swagger.v3.oas.models.security.SecurityRequirement;
 import io.swagger.v3.oas.models.security.SecurityScheme;
 import io.swagger.v3.oas.models.servers.Server;


 import org.springdoc.core.models.GroupedOpenApi;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;



@Configuration
public class  SwaggerConfig {
	

    @Value("${BASE-URL-SWAGGER}")
	private String urlServer ;

	private SecurityScheme createAPIKeyScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("bearer");
	}
	

    @Bean
OpenAPI myOpenAPI() {

    Contact contact = new Contact();
    contact.setEmail("elbekriomayma@gmail.com");
    contact.setName("Omayma");
    contact.setUrl("https://www.swagger.ma");

    License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

    Info info = new Info()
            .title("Digital Banking API")
            .version("2.0")
            .contact(contact)
            .description("This Digital Banking API allows clients to interact with banking services digitally. "
                    + "It covers a wide range of functionalities including account management, transaction processing, "
                    + "online payments, and financial reporting. Users can securely authenticate, view account balances, "
                    + "initiate transfers, and manage their personal finance data through our endpoints. "
                    + "This documentation provides detailed information on how to utilize the API to its full potential, "
                    + "including examples of requests and responses for each available operation. "
                    + "For additional support or inquiries, please reach out to our customer service team.")
            .termsOfService("")
            .license(mitLicense);

    return new OpenAPI()
                        .addSecurityItem(new SecurityRequirement()
                            .addList("Bearer Authentication"))
                            .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                        .info(info)
                        .addServersItem(new Server().url(urlServer).description("Server"));

}


    @Bean
    GroupedOpenApi presentation() { // group all APIs with `operation` in the path
      return GroupedOpenApi.builder().group("1- PRESENTATION").pathsToMatch("").build();
    }
    @Bean
    GroupedOpenApi login() { // group all APIs with `operation` in the path
      return GroupedOpenApi.builder().group("2- UTILISATEUR").pathsToMatch("/**/user/**").build();
    }
    @Bean
    GroupedOpenApi referentiel() { // group all APIs with `operation` in the path
      return GroupedOpenApi.builder().group("5- accounts").pathsToMatch("/accounts/**").build();
    }

    @Bean
    GroupedOpenApi All() { // group all APIs with `operation` in the path
        return GroupedOpenApi.builder().group(" ALL").pathsToMatch("/**").build();
    }
    

}
