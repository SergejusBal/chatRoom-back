package ChatNewPage.Chat.Services;

import ChatNewPage.Chat.Models.ChatMessage;
import ChatNewPage.Chat.Repositories.MessageRepository;
import ChatNewPage.Chat.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    public void registerMessage(ChatMessage chatMessage, String Authorization){

        int userIDFromJWT = userService.userIDFromJWT(Authorization);
        int userIDFromUserName = userRepository.getUserID(chatMessage.getName());

        if(userIDFromJWT == userIDFromUserName)  messageRepository.registerMessage(chatMessage);
    }

    public List<ChatMessage> getLast100Messages(){
        return messageRepository.getLast100Messages();
    }


}
