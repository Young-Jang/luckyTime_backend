//package toyproject.almigty.config.auth;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import toyproject.almigty.board.application.CustomOAuth2UserService;
//
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/", "/Savory-gh-pages/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/hello")
//                .and()
//                .logout().logoutSuccessUrl("/")
//                .and()
//                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
//
//    }
//}