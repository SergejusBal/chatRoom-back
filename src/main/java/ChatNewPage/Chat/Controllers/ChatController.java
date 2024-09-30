package ChatNewPage.Chat.Controllers;


import ChatNewPage.Chat.Models.ChatMessage;
import ChatNewPage.Chat.Models.MessageType;
import ChatNewPage.Chat.Models.Name;
import ChatNewPage.Chat.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;

    private HashMap<String, String> namelist = new HashMap<>();

    private String[] logoutMessages = {
            " has left the chat.",
            " has logged out. See you next time!",
            " has exited the session.",
            " has disconnected.",
            " has signed off.",
            " has left the conversation.",
            " has gone offline.",
            " has departed.",
            " is no longer available.",
            " has exited. Farewell!"
    };

    private String[] loginMessages = {
            " has joined the chat.",
            " has logged in. Welcome aboard!",
            " has entered the room.",
            " is now online.",
            " has connected successfully.",
            " has joined the conversation.",
            " is now part of the session.",
            " has appeared online.",
            " has arrived. Let the chat begin!",
            " has signed in and is ready to chat."
    };

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage repeatMessage(ChatMessage chatMessage,
                                     @Header("simpSessionId") String sessionId,
                                     @Header("Authorization") String authorization) throws Exception {

        if(chatMessage.getType().equals(MessageType.SYSTEM)){
            String name = chatMessage.getName();
            chatMessage.setMessage(name + getRandomLofInMessage());
            return chatMessage;
        }

        chatMessage.setSesionID(sessionId);
        messageService.registerMessage(chatMessage,authorization);
        return chatMessage;
    }

    @MessageMapping("/name")
    @SendTo("/topic/name")
    public List<String> setName(Name name,
                                @Header("simpSessionId") String sessionId) throws Exception {

       namelist.put(sessionId, name.getName());

       return new ArrayList<>(new HashSet<>(namelist.values()));
    }

    @MessageMapping("/oldMessages")
    @SendToUser("/topic/oldMessages")
    public List<ChatMessage> getOldMessages() throws Exception {

        return messageService.getLast100Messages();
    }

    public void removeUser(String sessionId)  {
        String name = namelist.remove(sessionId);

        if(name != null) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(MessageType.SYSTEM);
            chatMessage.setMessage(name + getRandomLogOffMessage());

            messagingTemplate.convertAndSend("/topic/name", new ArrayList<>(namelist.values()));
            messagingTemplate.convertAndSend("/topic/chat", chatMessage);
        }
    }


    private String getRandomLogOffMessage(){
        Random random = new Random();
        return logoutMessages[random.nextInt(0,9)];
    }
    private String getRandomLofInMessage(){
        Random random = new Random();
        return loginMessages[random.nextInt(0,9)];
    }

    public void sendPersonalMessage(String sessionID, String destination, ChatMessage message) {
        messagingTemplate.convertAndSendToUser(sessionID, destination, message);
    }


}
