package cn.nottingham.uav.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Intercepter Configure include the black list and white list
 * @author XuhuiWei
 *
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor loginInterceptor = new LoginInterceptor();
		InterceptorRegistration ir =  registry.addInterceptor(loginInterceptor);
		/*The black list*/
		ir.addPathPatterns("/**");
		
		/*The white list*/
		List<String> patterns = new ArrayList<String>();

		patterns.add("/assets/**");
		patterns.add("/bootstrap3/**");
		patterns.add("/bower_components/**");
		patterns.add("/dist/**");
		patterns.add("/images/**");
		patterns.add("/js/**");
		patterns.add("/plugins/**");
		patterns.add("/users/get_user_jsonp");
		patterns.add("/web/login.html");
		patterns.add("/web/register.html");
		patterns.add("/web/index.html");
		patterns.add("/users/login");
		patterns.add("/web/admin-login.html");
		patterns.add("/users/reg");
		patterns.add("/websocket/*");
		patterns.add("/web/NewFile.html");
		patterns.add("/adms/login");
		ir.excludePathPatterns(patterns);
	}
	
}
