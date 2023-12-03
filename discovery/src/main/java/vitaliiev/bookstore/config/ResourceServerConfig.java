package vitaliiev.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c -> c
                .requestMatchers("/actuator/**").permitAll());
        http.authorizeHttpRequests(c -> c
                        // TODO: 03.12.2023 isAuthenticated
                        .requestMatchers("/eureka/**").permitAll()) // filter calls on gateway
//                .oauth2ResourceServer(c -> c.jwt(Customizer.withDefaults()))
//                .httpBasic(Customizer.withDefaults())
                .csrf(c -> c.ignoringRequestMatchers("/eureka/**"));
        http.authorizeHttpRequests(c -> c.requestMatchers("/*").authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

}
