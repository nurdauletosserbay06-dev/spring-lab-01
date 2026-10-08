
package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.Audited;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class AuditAspect {

    @Around("@annotation(audited)")
    public Object audit(ProceedingJoinPoint pjp,
                        Audited audited) throws Throwable {

        String action = audited.action();

        System.out.println("[AUDIT] start " + action
                + " at " + LocalDateTime.now());

        if (audited.logArguments()) {
            System.out.println("[AUDIT] args="
                    + Arrays.toString(pjp.getArgs()));
        }

        try {
            Object result = pjp.proceed();
            System.out.println("[AUDIT] " + action + " success");
            return result;
        } catch (Throwable ex) {
            System.out.println("[AUDIT] " + action
                    + " failure: " + ex.getMessage());
            throw ex;
        }
    }
}
