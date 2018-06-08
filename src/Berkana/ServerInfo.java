package Berkana;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ServerInfo {
    private final String serverName;
    private BusyInfoStatus busyInfo;
    private static final String API_URL
            = "/DodontoFServer.rb?webif=getBusyInfo";

    public ServerInfo(String serverName) {
        this.serverName = serverName;
        busyInfo = new BusyInfoStatus(-1,-1,"","failed");
    }

    public void reloadBusyInfo(String serverUrl) {
        URL url = null;
        try {
            // urlの生成とhttps接続の準備
            url = new URL(serverUrl + API_URL);
            String json = CallAPI.getJson(url);
            ObjectMapper map = new ObjectMapper();
            busyInfo = map.readValue(json, BusyInfoStatus.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLoginCount() {
        return busyInfo.loginCount;
    }

    public int getMaxLoginCount() {
        return busyInfo.maxLoginCount;
    }

    public String getResult() {
        return busyInfo.result;
    }

    public String getVersion() {
        return busyInfo.version;
    }
}

