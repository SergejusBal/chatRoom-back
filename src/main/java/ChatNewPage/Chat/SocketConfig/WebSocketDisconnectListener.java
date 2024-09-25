package ChatNewPage.Chat.SocketConfig;

import ChatNewPage.Chat.Controllers.ChatController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketDisconnectListener {

    @Autowired
    private ChatController chatController;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();

    //    chatController.removeUser(sessionId);
//
    }
}