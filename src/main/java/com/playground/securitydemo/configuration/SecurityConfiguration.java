package com.playground.securitydemo.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration // This annotation declares that this class is a source of bean definitions for the application context.
@EnableWebSecurity // This annotation switches on Spring Security's web security support.
public class SecurityConfiguration {

    // Define the security filter chain
    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http
            // Define authorization rules for HTTP requests
            .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers("/", "/welcome").permitAll() // Allow all users to access "/" and "/welcome"
                    .requestMatchers("/authenticated").hasRole("USER") // Only allow users with ADMIN role to access "/authenticated"
                    .requestMatchers("/customError").permitAll() // Allow all users to access "/customError"
                    .anyRequest().denyAll() // Deny all other requests
            )

            // Enable CSRF protection with default settings
            .csrf(Customizer.withDefaults())

            // Configure "remember me" functionality
            .rememberMe((remember) -> remember
                    .rememberMeParameter("remember-me")
                    .key("uniqueAndSecretKey")
                    .tokenValiditySeconds(1000)
                    .rememberMeCookieName("rememberloginnardone")
                    .rememberMeParameter("remember-me")
            )

            // Configure session management
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .maximumSessions(1)
            )

            // Configure form login
            .formLogin((form) -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/authenticated")
                    .failureUrl("/login?error=true")
                    .failureHandler(authenticationFailureHandler())
                    .permitAll()
            )

            // Configure logout
            .logout((logout) -> logout
                    .logoutSuccessUrl("/welcome")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
            );

        // Build and return the security filter chain
        return http.build();
    }

    // Define the authentication failure handler
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    // Define the HTTP session event publisher
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    // Define the user details service
    @Bean
    public UserDetailsService userDetailsService(){

        // Define a user
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        // Define an admin
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
                .build();

        // Return a new in-memory user details manager with the defined users
        return new InMemoryUserDetailsManager(user, admin);
    }

    // Define the password encoder
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*  to use Digest Authentication

        DigestAuthenticationEntryPoint entryPoint() {
        DigestAuthenticationEntryPoint result = new DigestAuthenticationEntryPoint();
        result.setRealmName("My Security App Realm");
        result.setKey("3028472b-da34-4501-bfd8-a355c42bdf92");
    }

    @Autowired
    UserDetailsService userDetailsService;

    DigestAuthenticationFilter digestAuthenticationFilter() {
        DigestAuthenticationFilter result = new DigestAuthenticationFilter();
        result.setUserDetailsService(userDetailsService);
        result.setAuthenticationEntryPoint(entryPoint());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ...
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint()))
                .addFilterBefore(digestFilter());
        return http.build();
    } */

}