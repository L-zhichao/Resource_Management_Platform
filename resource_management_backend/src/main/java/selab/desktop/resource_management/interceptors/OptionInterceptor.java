package selab.desktop.resource_management.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class OptionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().toUpperCase().equals("OPTIONS")){
            return true;   //通过所有OPTION请求
        }
        if(request.getSession().getAttribute("username") == null){
            return false;
        }
        return true;
    }
}
