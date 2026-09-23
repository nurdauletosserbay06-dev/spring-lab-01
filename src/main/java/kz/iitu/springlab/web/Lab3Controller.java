package kz.iitu.springlab.web;

import kz.iitu.springlab.config.AppProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/lab3")
public class Lab3Controller {

    private final AppProperties properties;

    public Lab3Controller(AppProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/config")
    public AppProperties getConfig() {
        return properties;
    }

    @GetMapping("/variant")
    public Map<String, Object> getVariantConfig() {
        return Map.of(
                "jwtSecret", properties.getSecurity().getJwtSecret(),
                "tokenValidity", properties.getSecurity().getTokenValidity().toString()
        );
    }
}