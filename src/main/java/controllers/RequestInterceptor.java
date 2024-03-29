package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Actor;
import services.ActorService;

public class RequestInterceptor implements HandlerInterceptor {
    @Autowired private ActorService actorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        Actor principal = actorService.findPrincipal();

        // Redirect blocked users to the blocked page.
        if (principal != null && principal.getBanned() && !request.getServletPath().equals("/welcome/blocked.do")) {
            response.sendRedirect(request.getContextPath()+"/welcome/blocked.do");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {

    }
}
