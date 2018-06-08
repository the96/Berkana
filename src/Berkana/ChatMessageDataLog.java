package Berkana;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ChatMessageDataLog{
    private long time;
    private HashMap<String, Object> hashMap;
    private static final String CUTINCOMMAND ="###CutInCommand:rollVisualDice###";
    private static final String DICEBOT = "DiceBot : ";
    public ChatMessageDataLog(double time, HashMap<String, Object> hashMap) {
        this.time = (long) time;
        // dicebotでダイスを転がすとカットインコマンド用のJsonが飛んでくることがあるので、ダイスの結果だけを取り出す
        String message = (String)hashMap.get("message");
        if (message.startsWith(CUTINCOMMAND)) {
            message = message.substring(message.indexOf(DICEBOT) + DICEBOT.length(),message.indexOf("randResults") -3);
            hashMap.replace("message", message);
        }
        this.hashMap = hashMap;
    }

    public double getMilliTime() {
        return time;
    }
    public int getColor() {
        return (int) hashMap.get("color");
    }
    public int  getChannel() {
        return (int) hashMap.get("channel");
    }
    public String getMessage() {
        return (String) hashMap.get("message");
    }
    public String getSenderName() {
        return (String) hashMap.get("senderName");
    }
    public String getUniqueId() {
        return (String) hashMap.get("uniqueId");
    }
    public String toString() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String date = simpleDateFormat.format(cal.getTime());
        return date + "\r\n" + getSenderName() + ": " + getMessage();
    }
}