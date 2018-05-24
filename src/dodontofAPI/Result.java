package dodontofAPI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Result {
    private String result;
    private List<ChatMessageDataLog> chatMessageDataLog;
    private double latestChatTime;
    @JsonCreator
    private Result(@JsonProperty("result") String result, @JsonProperty("chatMessageDataLog") List<List<Object>> chatObject) {
        this.result = result;
        this.chatMessageDataLog = new ArrayList<>();
        this.latestChatTime = 0;
        for (List<Object> obj : chatObject) {
            this.chatMessageDataLog.add(new ChatMessageDataLog((double) obj.get(0),(HashMap<String, Object>) obj.get(1)));
            this.latestChatTime = Math.max(this.latestChatTime,(double) obj.get(0)+0.000001);
        }
    }
    public List<ChatMessageDataLog> getChatMessageDataLog() {
        return this.chatMessageDataLog;
    }
    public double getLatestChatTime(){return this.latestChatTime;}
}
