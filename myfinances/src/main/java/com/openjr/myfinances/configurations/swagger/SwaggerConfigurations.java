package com.openjr.myfinances.configurations.swagger;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openjr.myfinances.model.User;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@Configuration
public class SwaggerConfigurations {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.openjr.myfinances"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.apiInfo())
				.ignoredParameterTypes(User.class)
				.securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
                .title ("My Finances API")
                .description ("This API is to register finances life")
                .license("todo")
                .licenseUrl("todo")
                .termsOfServiceUrl("/todouri")
                .version("1.0.0")
                .contact(new Contact("OPENJR","com.openjr.myfinances", "todo#"))
                .build();
	}
	
	
	private SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("/auth");
        return new OAuthBuilder().name("oauth2schema")
                .grantTypes(Arrays.asList(grantType))
                .scopes(Collections.emptyList())
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference("oauth2schema", new AuthorizationScope[0])))
                .operationSelector(each -> true)
                .build();
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return SecurityConfigurationBuilder
                .builder()
                .scopeSeparator(" ")
                .build();
    }
}
