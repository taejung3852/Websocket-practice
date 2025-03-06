package playground.websocket.socket;

import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SocketTextHandler extends TextWebSocketHandler { // Spring에서 제공하는 TextWebSockethandler를 상속받아서 텍스트 기반 웹소켓 메시지를 처리할 수 있게 만든다. (binary 타입 웹소켓 핸들러도 지원한다.)
    private Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet(); // 여러 스레드가 동시에 접근하여 데이터를 추가하거나 삭제해도 문제없이 동작

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session); // 새로 연결된 client session을 sessions에 추가
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String payload = message.getPayload(); // 클라이언트가 보낸 메시지의 내용을 가져옵니다. 예를 들어, {"user": "홍길동"} 같은 JSON 문자열이 올 수 있다.
            JSONObject jsonObject = new JSONObject(payload); // 받은 메시지를 JSON 객체로 변환한다.
            String response = "hi" + jsonObject.get("user") + "! How may I help you"; // jsonObject에서 user 키에 해당하는 밸류를 꺼내와서 글로 만들었다.
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {  // 세션이 열려있는지 확인
                    s.sendMessage(new TextMessage(response)); // 열려있으면 응답 메시지 보내기
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session); //끊겨있으면 세션 지우기.
    }
}
