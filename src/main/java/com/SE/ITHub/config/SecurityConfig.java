package com.SE.ITHub.config;

import com.SE.ITHub.security.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.frontend.url}")
    private String primaryFrontendUrl;

    @Value("${app.frontend.urls:${app.frontend.url}}")
    private String allFrontendUrls;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String[] allowedOrigins = Arrays.stream(allFrontendUrls.split(","))
                        .map(String::trim)
                        .toArray(String[]::new);

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Store the original frontend URL if it's from allowed origin
                            String origin = request.getHeader("Origin");
                            if (origin != null && isValidFrontendUrl(origin)) {
                                request.getSession().setAttribute("frontend_origin", origin);
                            }
                            response.sendRedirect("/oauth2/authorization/google");
                        })
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            String frontendOrigin = (String) request.getSession().getAttribute("frontend_origin");
                            String targetUrl = primaryFrontendUrl + "/admin/portfolio";

                            if (frontendOrigin != null && isValidFrontendUrl(frontendOrigin)) {
                                targetUrl = frontendOrigin + "/admin/portfolio";
                                request.getSession().removeAttribute("frontend_origin");
                            }

                            response.sendRedirect(targetUrl);
                        })
                        .failureHandler((request, response, exception) -> {
                            String frontendOrigin = (String) request.getSession().getAttribute("frontend_origin");
                            String targetUrl = primaryFrontendUrl + "/login?error=true";

                            if (frontendOrigin != null && isValidFrontendUrl(frontendOrigin)) {
                                targetUrl = frontendOrigin + "/login?error=true";
                                request.getSession().removeAttribute("frontend_origin");
                            }

                            response.sendRedirect(targetUrl);
                        })
                );

        return http.build();
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {

                            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
                            response.sendRedirect(primaryFrontendUrl + "/oauth-success");
                        })
                        .failureUrl(primaryFrontendUrl + "/login?error=true")
                );

        return http.build();
    }
    private boolean isValidFrontendUrl(String url) {
        if (url == null) return false;
        return Arrays.stream(allFrontendUrls.split(","))
                .map(String::trim)
                .anyMatch(url::startsWith);
    }
}
