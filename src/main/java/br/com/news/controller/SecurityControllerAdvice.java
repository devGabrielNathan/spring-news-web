package br.com.news.controller;

import br.com.news.entity.Author;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityControllerAdvice {

    @ModelAttribute("isLoginPage")
    public boolean isLoginPage(HttpServletRequest request) {
        return "/login".equals(request.getRequestURI());
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            return auth;
        }
        var session = request.getSession(false);
        if (session != null) {
            Object contextObj = session.getAttribute("SPRING_SECURITY_CONTEXT");
            if (contextObj instanceof SecurityContext secContext) {
                return secContext.getAuthentication();
            }
        }
        return null;
    }

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated(HttpServletRequest request) {
        Authentication auth = getAuthentication(request);
        return auth != null && auth.isAuthenticated() &&
               !(auth instanceof AnonymousAuthenticationToken);
    }

    @ModelAttribute("currentUser")
    public Author currentUser(HttpServletRequest request) {
        Authentication auth = getAuthentication(request);
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof Author) {
            return (Author) principal;
        }
        return null;
    }

    @ModelAttribute("isEditor")
    public boolean isEditor(HttpServletRequest request) {
        Authentication auth = getAuthentication(request);
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return false;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof Author author) {
            return author.isEditor();
        }
        return false;
    }
}
