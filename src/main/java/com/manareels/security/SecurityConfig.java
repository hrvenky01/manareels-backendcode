/*
 * package com.manareels.security;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.web.SecurityFilterChain;
 * 
 * @Configuration public class SecurityConfig {
 * 
 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
 * throws Exception {
 * 
 * http .csrf(csrf -> csrf.disable()) .authorizeHttpRequests(auth -> auth
 * .requestMatchers("/api/auth/**").permitAll() // 🔓 LOGIN OPEN
 * .anyRequest().authenticated() ) .httpBasic(httpBasic -> httpBasic.disable())
 * // ❌ DISABLE BASIC AUTH .formLogin(form -> form.disable()); // ❌ DISABLE FORM
 * LOGIN
 * 
 * return http.build(); } }
 */