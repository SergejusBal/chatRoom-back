package ChatNewPage.Chat.Controllers;


import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {


    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Message repeatMessage(Message message) throws Exception {
        System.out.println("Test1");
        return message;
    }





}
