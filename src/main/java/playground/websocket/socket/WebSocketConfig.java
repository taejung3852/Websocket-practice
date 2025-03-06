package playground.websocket.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(new SocketTextHandler(), "/user") // `/user`로 들어오는 웹소켓 요청을 SocketTextHandler에서 처리
                .setAllowedOrigins("*") // 웹소켓 허용 범위를 지정하는 것이다. *은 전체 도메인에서 들어오는 모든 웹소켓을 허용한다는 의미. 실제 서비스에서는 특정 도메인을 작성한다.
                .withSockJS(); // SockJS 라이브러리를 활성화하는 함수다. 웹소켓을 지원하지 않는 오래된 브라우저에서도 비슷한 기능을 할 수 있게 한다.
    }

    /*
        ```md
        # 쉽게 말해서 이 WebSocketConfig.java 클래스란?

        `Spring`한테 `Websocket`을 켜고 `/user`경로로 들어오는 요청은
        모두 `SocketTextHandler`로 처리해 라고 설정해주는 것이다.
        ```
    */
}
