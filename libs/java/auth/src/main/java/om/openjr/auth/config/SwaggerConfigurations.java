package om.openjr.auth.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfigurations {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.edu.ufcg.virtus.testrec"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.apiInfo())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Collections.singletonList(HttpAuthenticationScheme
					    .JWT_BEARER_BUILDER
					    .name("JWT")
					    .build()));
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
                .title ("Risk Control API")
                .description ("This API ...")
                .license("todo")
                .licenseUrl("todo")
                .termsOfServiceUrl("/todouri")
                .version("1.0.0")
                .contact(new Contact("TODO","TODOMAIL", "todo#"))
                .build();
	}
	
    
    private SecurityContext securityContext() { 
        return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
    } 

    private List<SecurityReference> defaultAuth() { 
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
    }
}
