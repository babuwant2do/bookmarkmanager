package com.wordpress.babuwant2do.bookmarkmanager.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;


@Aspect
public class LoggingAspect {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	 private final Environment env;

	    public LoggingAspect(Environment env) {
	        this.env = env;
	    }
	
	 /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    
    @Pointcut(	"within(com.wordpress.babuwant2do.bookmarkmanager.repository..*)"+
            " || within(com.wordpress.babuwant2do.bookmarkmanager.service..*)"+
            " || within(com.wordpress.babuwant2do.bookmarkmanager.web.rest..*)")
        public void applicationPackagePointcut() {
            // Method is empty as this is just a Pointcut, the implementations are in the advices.
        }
    
	
	@AfterThrowing(pointcut="springBeanPointcut() && applicationPackagePointcut()", throwing="e")
	public void logAfterExcepton(JoinPoint joinPoint, Throwable e){
		e.printStackTrace();
	}
	
	@Around("applicationPackagePointcut() && springBeanPointcut()")
	public Object logAfterExceptonAround(ProceedingJoinPoint joinPoint) throws Throwable{
		  log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
	                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		 try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
	}
}
