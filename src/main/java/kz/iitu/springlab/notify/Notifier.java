package kz.iitu.springlab.notify;

public interface Notifier {
    String send(String message);   // возвращает отправленное сообщение
    String channel();              // имя канала для отчета
}
