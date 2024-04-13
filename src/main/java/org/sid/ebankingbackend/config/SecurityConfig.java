package org.sid.ebankingbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	   public static final String ADMIN = "admin";
	   public static final String USER = "user";

	@Autowired
    private final JwtAuthConverter jwtAuthConverter ;

   

	@Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
        		.cors().and()
                .authorizeHttpRequests(auth ->
                {
                    auth.requestMatchers("/api/v1/ramadan/user/**","/api/v1/ramadan/**","/","/swagger-ui/**", "/v3/api-docs/**","/api/v2/spring/referentiel/**").permitAll();
                    auth.anyRequest().authenticated();
                });

        http.
		        oauth2ResourceServer(oauth2 -> oauth2.jwt(
		                jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
		        ));

        http.
                sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

        
    }
	
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	          CorsConfiguration configuration = new CorsConfiguration();
	          //configuration.setAllowedOriginPatterns(Arrays.asList(https://*.DOMAINE));
	          configuration.setAllowedOriginPatterns(Arrays.asList("*"));
	          configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
	          configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token","client_uuid"));
	          configuration.setExposedHeaders(Arrays.asList("x-auth-token", "X-File-Name"));
	          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	          source.registerCorsConfiguration("/**", configuration);
	          return source;
	    }
	
	
	
}
