package com.eunji.look_at_this.config

import com.eunji.look_at_this.api.entity.Member
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.api.service.MemberService
import com.eunji.look_at_this.common.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Service


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


//    @Bean
//    fun userDetailsService(bCryptPasswordEncoder: BCryptPasswordEncoder): UserDetailsService {
//        val users: User.UserBuilder = User.builder()
//        val userDebug: UserDetails = users
//            .username("nagosoo2")
//            .password(bCryptPasswordEncoder.encode("\$2a\$10\$pExdAHEOR3LCpODWZYooX.lBNBvJ41XzRlApnqm1eQttDXIeEBPM6"))
//            .roles("USER")
//            .build()
//        val userRelease: UserDetails = users
//            .username("nagosoo")
//            .password(bCryptPasswordEncoder.encode("\$2a\$10\$k3qKC1RNQSqU.1UWbj.P8eLLoO/aiMvmtdFloZJoXl326pOdpe97e"))
//            .roles("USER")
//            .build()
//        return InMemoryUserDetailsManager(userDebug,userRelease)
//        return PrincipalDetailService()
//    }
}

@Service
@RequiredArgsConstructor
class PrincipalDetailService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        var user: Member? = null
        try {
            user = memberRepository.getMembersByMemberEmail(username).get()
        } catch (e: Exception) {
            throw NotFoundException("아이디 혹은 비밀번호가 틀렸어ㅠ_ㅠ")
        }
        return User.builder()
            .username(user.memberEmail)
            .password(user.memberPassword)
            .roles("USER")
            .build()
    }
}