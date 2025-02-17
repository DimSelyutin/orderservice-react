package com.itqgroup.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * Конфигурация интерсептора для реквестов.
 */
@Slf4j
@Component
public class InterceptorConfig implements HandlerInterceptor {

    private static final ThreadLocal<Long> TIMER = new ThreadLocal<>();
    private static final String MDC_UUID_KEY = "requestId";
    private static final String REQUEST_HEADER = "X-Request-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        long startTime = System.currentTimeMillis();
        TIMER.set(startTime);
        String requestId = request.getHeader(REQUEST_HEADER);
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put(MDC_UUID_KEY, requestId);
        log.info("Incoming request: UUID={}, method={}, URI={}, from IP={}",
            requestId, request.getMethod(),
            request.getRequestURI(), request.getRemoteAddr());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("Completed processing request: UUID={}, URI={}, Status={}",
            MDC.get(MDC_UUID_KEY), request.getRequestURI(),
            response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        long startTime = TIMER.get();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if (ex != null) {
            log.error("Request raised an exception: UUID={}, URI={}, Exception={}",
                MDC.get(MDC_UUID_KEY), request.getRequestURI(),
                ex.getMessage(), ex);
        }
        log.debug("After completion: UUID={}, URI={}, Duration={} ms", MDC.get(MDC_UUID_KEY),
            request.getRequestURI(), duration);
        TIMER.remove();
        MDC.remove(MDC_UUID_KEY);
    }
}
