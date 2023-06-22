package ru.skypro.lessons.springboot.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Создаем bean SecurityFilterChain, который представляет
        // цепочку фильтров для обработки входящих запросов
        // в соответствии с настройками безопасности.

        http.csrf()
                .disable()
                // Отключаем CSRF-защиту,
                // так как будем использовать другие методы безопасности.

                .authorizeHttpRequests(this::customizeRequest);
        // Определяем правила для авторизации запросов
        // с помощью метода customizeRequest.

        return http.build();
        // Возвращаем сконфигурированную цепочку фильтров безопасности.
    }

    private void customizeRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        try {
            registry.requestMatchers(new AntPathRequestMatcher("/admin/**"))
                    .hasAnyRole("ADMIN")
                    // Определяем правило авторизации для запросов
                    // к URL, которые начинаются с "/admin/",
                    // позволяя доступ только пользователям с ролью "ADMIN".

                    .requestMatchers(new AntPathRequestMatcher("/**"))
                    .hasAnyRole("USER")
                    // Определяем правило авторизации для остальных запросов,
                    // позволяя доступ только пользователям с ролью "USER".

                    .and()
                    .formLogin().permitAll()
                    // Позволяем всем пользователям доступ к форме входа.

                    .and()
                    .logout().logoutUrl("/logout");
            // Настраиваем механизм выхода из системы
            // с заданием URL "/logout".

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource,
                                                 AuthenticationManager authenticationManager) {

        // Инициализируем JdbcUserDetailsManager с dataSource
        // и authenticationManager для работы с базой данных
        JdbcUserDetailsManager jdbcUserDetailsManager =
                new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
//
//        UserDetails ivan = User.withUsername("Ivan")
//                .password(passwordEncoder.encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails vladimir = User.withUsername("Pavel")
//                .password(passwordEncoder.encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("123"))
//                .roles("USER","ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(ivan, vladimir, admin);
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
