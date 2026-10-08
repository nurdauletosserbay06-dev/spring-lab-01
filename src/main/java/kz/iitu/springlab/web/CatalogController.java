
package kz.iitu.springlab.web;

import kz.iitu.springlab.service.CatalogService;
import kz.iitu.springlab.service.RemoveTwiceService;

import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab4")
public class CatalogController {

    private final CatalogService catalogService;
    private final RemoveTwiceService removeTwiceService;

    public CatalogController(
            CatalogService catalogService,
            RemoveTwiceService removeTwiceService) {
        this.catalogService = catalogService;
        this.removeTwiceService = removeTwiceService;
    }

    // GET: получение одного товара
    @GetMapping("/item/{id}")
    public String getItem(@PathVariable long id) {
        return catalogService.findById(id);
    }

    // GET: получение списка товаров
    @GetMapping("/items")
    public List<String> getItems(
            @RequestParam(defaultValue = "5") int limit) {
        return catalogService.findAll(limit);
    }

    // DELETE: удаление товара
    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable long id) {
        return catalogService.remove(id);
    }

    // Информация о Spring AOP Proxy
    @GetMapping("/proxy")
    public Map<String, String> proxyInfo() {
        return Map.of(
                "className",
                catalogService.getClass().getName(),

                "superClass",
                catalogService.getClass()
                        .getSuperclass().getSimpleName(),

                "isAopProxy",
                String.valueOf(
                        AopUtils.isAopProxy(catalogService)),

                "isCglib",
                String.valueOf(
                        AopUtils.isCglibProxy(catalogService))
        );
    }

    // Правильный вызов через другой Spring Bean
    @GetMapping("/remove-twice/{id}")
    public String removeTwice(@PathVariable long id) {
        return removeTwiceService.removeTwice(id);
    }

    // Self-Invocation: внутренние вызовы обходят Proxy
    @GetMapping("/self-invocation/{id}")
    public String selfInvocation(@PathVariable long id) {
        return catalogService.removeTwice(id);
    }
}
