
package kz.iitu.springlab.service;

import org.springframework.stereotype.Service;

@Service
public class RemoveTwiceService {

    private final CatalogService catalogService;

    public RemoveTwiceService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public String removeTwice(long id) {
        String first = catalogService.remove(id);
        String second = catalogService.remove(id);
        return first + " | " + second;
    }
}
