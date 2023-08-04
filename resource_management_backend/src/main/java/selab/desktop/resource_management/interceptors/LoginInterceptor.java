package selab.desktop.resource_management.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){

            return true;    //通过所有OPTION请求
        }
        return true;
    }
}
