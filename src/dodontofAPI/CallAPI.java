package dodontofAPI;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallAPI {
    public static String getJson(URL url) {
        String json = "";
        try {
            // urlの生成とhttps接続の準備
            System.out.println(url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.addRequestProperty("User-Agent", "TrpgResourceManager/the96");
            con.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoOutput(false);
            con.setDoInput(true);

            // httpsリクエスト
            con.connect();
            int status = con.getResponseCode();
            System.out.println("Connect URL:" + url);
            System.out.println("Response Code: [" + status + "]");
            // 接続できたら中身を確認
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = con.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder strBuilder_Json = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    strBuilder_Json.append(line + "\r\n");
                }
                json = strBuilder_Json.toString();
                System.out.println("*****\r\n" + json + "*****");
                con.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
