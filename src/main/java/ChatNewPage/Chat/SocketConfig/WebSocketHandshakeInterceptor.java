package ChatNewPage.Chat.SocketConfig;

import ChatNewPage.Chat.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
@Component
@EnableWebSocketMessageBroker
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {


    @Autowired
    private UserService userService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        String JWTFromURI = request.getURI().getQuery();
        if (userService.userAutoLogIn(JWTFromURI)) return super.beforeHandshake(request, response, wsHandler, attributes);
        else return false;

    }
}
