package book.manager.initializer;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.ServletContext;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    //这里实际上会自动注册一个Filter，SpringSecurity底层就是依靠N个过滤器实现的


    //添加Filter放置在SpringSecurity过滤链的最前面，处理提交的中文数据乱码问题
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        servletContext.addFilter("characterEncodingFilter",new CharacterEncodingFilter("UTF-8",true))
                .addMappingForUrlPatterns(null,false,"/*");
        super.beforeSpringSecurityFilterChain(servletContext);
    }
}
