package accounts.web;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ActuatorSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
            .withUser("actuator").password(passwordEncoder.encode("actuator")).roles("ACTUATOR").and()
            .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN", "ACTUATOR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // TODO-20: Configure access control to actuator endpoints
        // - Anybody can access "health" and "info" endpoints
        // - ADMIN role can access "conditions" endpoint
        // - ACTUATOR role can access all the other endpoints
        http.authorizeRequests()
            .requestMatchers(/* Add code here */).permitAll()
            .requestMatchers(/* Add code here */).hasRole("ADMIN")
            .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }
}
