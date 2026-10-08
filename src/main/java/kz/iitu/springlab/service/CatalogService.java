
package kz.iitu.springlab.service;

import kz.iitu.springlab.annotation.TrackTime;
import kz.iitu.springlab.audit.Audited;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class CatalogService {

    // Получение товара по ID
    // Этот метод не аудируется
    public String findById(long id) {
        sleep(50);
        return "Item no. " + id;
    }

    // Получение списка товаров
    // Аудит с записью аргументов
    @Audited(action = "CATALOG_LIST", logArguments = true)
    @TrackTime
    public List<String> findAll(int limit) {
        sleep(300);

        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "Item no. " + i)
                .toList();
    }

    // Удаление товара
    // Аудит успешных и ошибочных операций
    @Audited(action = "CATALOG_REMOVE")
    public String remove(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid identifier: " + id);
        }

        return "Removed item no. " + id;
    }

    // Демонстрация Self-Invocation
    // Внутренние вызовы обходят Spring AOP Proxy
    public String removeTwice(long id) {
        String first = this.remove(id);
        String second = this.remove(id + 1);

        return first + "; " + second;
    }

    // Имитация задержки базы данных
    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
