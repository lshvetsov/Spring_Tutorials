package ge.lshvetsov.simplepayroll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:4200"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Bean      // TODO two modes: dev and prod
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    // TODO Finish the configuration on CSRF
    /*
     * The problem with CSRF is that even though Angular frontend capture the token from GET requests,
     * when it's used for other request -> 401 (unauthorized) and another token in Set-Cookie
     */

    HttpSecurity httpSecurity = http
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/h2/**").permitAll()
        .anyRequest().authenticated())
      .headers(headers -> headers
        .contentSecurityPolicy(contentSecurityPolicy -> contentSecurityPolicy
          .policyDirectives("frame-ancestors 'self'")))
      .csrf(csrf -> csrf.ignoringRequestMatchers("/h2/**")
        .csrfTokenRepository(csrfTokenRepository()))
//      .csrf(AbstractHttpConfigurer::disable)
      .httpBasic(Customizer.withDefaults());
    return httpSecurity.build();
  }

  @Bean
  public CsrfTokenRepository csrfTokenRepository() {
    CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
    repository.setCookiePath("/");
    return repository;
  }

}
