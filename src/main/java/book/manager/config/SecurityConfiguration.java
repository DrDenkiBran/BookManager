package book.manager.config;

import book.manager.service.impl.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //继承WebSecurityConfigurerAdapter,之后会进行配置

    @Resource
    UserAuthService service;

    @Resource
    PersistentTokenRepository repository;

    //    jdbc实现数据库存放cookie
    @Bean
    public PersistentTokenRepository jdbcRepository(@Autowired DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();//使用基于JDBC的实现
        repository.setDataSource(dataSource);//配置数据源
//        repository.setCreateTableOnStartup(true);//启动时自动创建用于存储Token的表（建议第一次启动之后删除该行）
        return repository;
    }


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
                .antMatchers("/static/**","/login","/register","/api/auth/**").permitAll()  //静态资源，使用permitAll来运行任何人访问（注意一定要放在前面）
                .anyRequest().hasAnyRole("user","admin")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/api/auth/login")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")    //退出登陆的请求地址
                .logoutSuccessUrl("/login")   //退出后重定向的地址
                .and()
                .csrf().disable()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(repository)
                .tokenValiditySeconds(60 * 60 * 24 * 7);//Token的有效时间（秒）默认为14天

    }
}
