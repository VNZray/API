package nagaventures.backend.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/send")  // Maps messages sent to "/app/send"
    @SendTo("/topic/messages")  // Broadcasts the result to "/topic/messages"
    public String handleMessage(String message) {
        return "Server received: " + message;  // Process and return the message
    }
}
