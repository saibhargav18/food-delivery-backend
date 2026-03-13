package com.fooddelivery.food_delivery_backend.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public final JWTAuthenticationFilter jwtAuth;

    public SecurityConfig(JWTAuthenticationFilter jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/delivery-partners/register").permitAll()
                        // Browse restaurants - PUBLIC
                        .requestMatchers(HttpMethod.GET, "/api/restaurants/**").permitAll()

                        // Browse menu items - PUBLIC
                        .requestMatchers(HttpMethod.GET, "/api/menu-items/**").permitAll()

                        // Browse categories - PUBLIC
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()

                        // Browse reviews - PUBLIC
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()

                        // Browse coupons - PUBLIC
                        .requestMatchers(HttpMethod.GET, "/api/coupons").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/coupons/validate").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuth, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
