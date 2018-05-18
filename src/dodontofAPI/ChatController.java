package dodontofAPI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatController {
    private static final String API_URL
            = "DodontoFServer.rb?webif=chat";
    private static double latestGetTime;
    private ArrayList<ChatMessageDataLog> chatlog;
    private List<ChatMessageDataLog> lastAddedLog;
    public ChatController() {
        latestGetTime = 0;
        chatlog = new ArrayList<>();
    }
    public void reloadChatLog(String serverUrl, int roomNo, String password) {
        URL url = null;
        String passParam = password.isEmpty()?
                "":"&password=" + password;
        try {
            // urlの生成とhttps接続の準備
            String mill = String.format("%.6f",latestGetTime);
            url = new URL(serverUrl + API_URL + "&room=" + roomNo + passParam + "&time=" + mill);
            String json = CallAPI.getJson(url);
            ObjectMapper map = new ObjectMapper();
            Result result = map.readValue(json, new TypeReference<Result>() {});
            lastAddedLog = result.getChatMessageDataLog();
            chatlog.addAll(lastAddedLog);
            latestGetTime = Math.max(latestGetTime,result.getLatestChatTime());
            System.out.println(latestGetTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<ChatMessageDataLog> getChatlog() {
        return lastAddedLog;
    }
    public ChatMessageDataLog getChatlog(int index) {
        return chatlog.get(index);
    }
}

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