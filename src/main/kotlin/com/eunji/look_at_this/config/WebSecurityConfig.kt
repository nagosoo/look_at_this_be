package com.eunji.look_at_this.config

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
class WebSecurityConfig {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .httpBasic(Customizer.withDefaults())
            .csrf().disable()
            .cors().and()
            .authorizeHttpRequests()
            .requestMatchers("/member","member/login", "/api/v1/notification/dev", "/link/dev").permitAll()
            .anyRequest().authenticated()
            .and()
            .build()
    }

    @Bean
    fun userDetailsService(bCryptPasswordEncoder: BCryptPasswordEncoder): UserDetailsService {
        val users: User.UserBuilder = User.builder()
        val userDebug: UserDetails = users
            .username("nagosoo2")
            .password(bCryptPasswordEncoder.encode("\$2a\$10\$pExdAHEOR3LCpODWZYooX.lBNBvJ41XzRlApnqm1eQttDXIeEBPM6"))
            .roles("USER")
            .build()
        val userRelease: UserDetails = users
            .username("nagosoo")
            .password(bCryptPasswordEncoder.encode("\$2a\$10\$k3qKC1RNQSqU.1UWbj.P8eLLoO/aiMvmtdFloZJoXl326pOdpe97e"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(userDebug,userRelease)
    }
}
