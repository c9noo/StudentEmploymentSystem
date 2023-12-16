package com.employment.config;

import com.employment.filter.JwtAuthenticationTokenFilter;
import com.employment.handler.security.AccessDeniedHandlerImpl;
import com.employment.handler.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: StudentEmploymentSystem
 * @ClassName SecurityConfig
 * @author: c9noo
 * @create: 2023-11-06 19:45
 * @Version 1.0
 * Security的配置类
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                //配置请求验证
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/admin/user/login").anonymous()
                .antMatchers("/admin/user/captcha").anonymous()
                .antMatchers("/admin/common/email").anonymous()
                .antMatchers("/admin/common/reset").anonymous()
                .antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        http
                //将jwtAuthenticationTokenFilter添加到UsernamePasswordAuthenticationFilter中
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器 TODO 未生效
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        http
                //关闭默认注销功能
                .logout().disable();

        http
                //允许跨域
                .cors();
    }

    /**
     * 为security提供密码加密的算法
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 需要调用authenticate方法，传入UsernamePasswordAuthenticationToken对象
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
