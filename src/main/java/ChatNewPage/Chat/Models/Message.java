package ChatNewPage.Chat.Models;

import java.time.LocalDateTime;

public class Message {
    private String name;
    private String message;
    private LocalDateTime localDateTime;
    private MessageType type;

    public Message() {
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public MessageType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
