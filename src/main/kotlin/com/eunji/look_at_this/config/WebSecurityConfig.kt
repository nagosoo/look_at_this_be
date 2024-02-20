package com.eunji.look_at_this.config

import com.eunji.look_at_this.Constance
import com.eunji.look_at_this.api.entity.Member
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.common.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Service
import java.util.*


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
            .requestMatchers("/member", "member/login", "/api/v1/notification/dev", "/link/dev").permitAll()
            .anyRequest().authenticated()
            .and()
            .build()
    }

    @Autowired
    private lateinit var uds: UserDetailsService

    @Autowired
    private lateinit var encoder: BCryptPasswordEncoder

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(uds)
        authenticationProvider.setPasswordEncoder(encoder)
        return authenticationProvider
    }
}

@Service
class PrincipalDetailService : UserDetailsService {
    @Autowired
    private lateinit var userRepo: MemberRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Throws(NotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val member: Member =
            userRepo.findByMemberEmail(email) ?: throw NotFoundException(Constance.NOT_FOUND_MEMBER_ERROR)
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        authorities.add(GrantedAuthority { "ROLE_USER" })
        return User(
            member.memberEmail,
            passwordEncoder.encode(member.memberPassword),
            authorities
        )
    }
}
