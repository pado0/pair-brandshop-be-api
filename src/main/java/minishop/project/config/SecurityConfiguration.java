package minishop.project.config;

import minishop.project.security.JwtAuthenticationFilter;
import minishop.project.security.JwtTokenProvider;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

// todo : 문제가 있으면 이 파일 먼저 살펴보기. 최신 코드로 리펙한것이지만 깨름직
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    // 필터체인을 적용한다. 우선 테스트만
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // restapi 로 개발중이므로 기본설정 사용x. 기본설정은 로그인 폼으로 리다이렉트 됨
                .csrf().disable()// rest api이므로 csrf 보안 필요없음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// jwt는 session 사용 안하므로 생성 안함
                .and()
                   .authorizeRequests()// 다음 리퀘스트에 대한 사용 권한 체크
                        // 가입 및 인증 주소는 누구나 접근 가능
                        .antMatchers("/*/signin", "/*/signup").permitAll()
                        // helloworld로 시작하는 get 요청 리소스는 누구나 접근 가능
                        .antMatchers(HttpMethod.GET, "/helloworld/**").permitAll()
                        // 그 외 나머지 요청은 모두 인증된 회원만 접근 가능
                        // 다음과 같이 hasRole 내에서 관리됨 authority – the authority to require (i.e. ROLE_USER, ROLE_ADMIN, etc).
                        .anyRequest().permitAll()//hasRole("USER") // todo: hasRole 범위 정하기
               .and() // jwt token 필터를 id/password 인증 필터 전에 넣음
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // ignore할 페이지 링크 추가한다
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");

    }
}

