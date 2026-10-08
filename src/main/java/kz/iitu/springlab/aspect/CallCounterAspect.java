
package kz.iitu.springlab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class CallCounterAspect {

    private final Map<String, AtomicInteger> counters =
            new ConcurrentHashMap<>();

    @Before("kz.iitu.springlab.aspect.Pointcuts.serviceOperation()")
    public void countCall(JoinPoint joinPoint) {
        String methodName =
                joinPoint.getSignature().toShortString();

        int count = counters
                .computeIfAbsent(methodName,
                        key -> new AtomicInteger())
                .incrementAndGet();

        System.out.println("[COUNTER] " + methodName +
                " called " + count + " times");
    }

    public Map<String, Integer> getStatistics() {
        Map<String, Integer> result = new java.util.TreeMap<>();

        counters.forEach((method, count) ->
                result.put(method, count.get()));

        return result;
    }
}
