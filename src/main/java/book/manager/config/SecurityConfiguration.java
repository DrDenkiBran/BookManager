package book.manager.config;

import book.manager.service.UserAuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //继承WebSecurityConfigurerAdapter,之后会进行配置

    @Resource
    UserAuthService service;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //使用SpringSecurity提供的BcryptPasswordEncoder

//        直接认证
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(encoder)
//                .withUser("test")
//                .password(encoder.encode("123456"))
//                .roles("user");

//        数据库认证
        auth
                .userDetailsService(service)
                .passwordEncoder(encoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//首先需要配置那些请求会被拦截，哪些请求需要具有什么角色才能访问
                .antMatchers("/static/**").permitAll()  //静态资源，使用permitAll来运行任何人访问（注意一定要放在前面）
                .antMatchers("/**").hasRole("user")    //所有请求必须登陆并且是user角色才可以访问（不包含上面的静态资源）
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")    //退出登陆的请求地址
                .logoutSuccessUrl("/login")   //退出后重定向的地址
                .and()
                .csrf().disable();


    }
}
