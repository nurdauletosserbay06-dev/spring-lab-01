
package kz.iitu.springlab.web;

import kz.iitu.springlab.aspect.CallCounterAspect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CounterController {

    private final CallCounterAspect callCounterAspect;

    public CounterController(CallCounterAspect callCounterAspect) {
        this.callCounterAspect = callCounterAspect;
    }

    @GetMapping("/api/lab4/stats")
    public Map<String, Integer> getStatistics() {
        return callCounterAspect.getStatistics();
    }
}
