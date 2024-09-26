package ChatNewPage.Chat.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ChatMessage {
    private String name;
    private String message;
    @JsonProperty("reservationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
    private String sesionID;
    private MessageType type;

    public ChatMessage() {
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

    public String getSesionID() {
        return sesionID;
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

    public void setSesionID(String sesionID) {
        this.sesionID = sesionID;
    }
}
