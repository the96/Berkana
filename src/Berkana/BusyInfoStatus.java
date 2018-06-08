package Berkana;

import com.fasterxml.jackson.annotation.JsonProperty;
class BusyInfoStatus {
    @JsonProperty("loginCount")
    int loginCount;
    @JsonProperty("maxLoginCount")
    int maxLoginCount;
    @JsonProperty("version")
    String version;
    @JsonProperty("result")
    String result;
    public BusyInfoStatus() {}
    public BusyInfoStatus(int loginCount, int maxLoginCount, String version, String result) {
        this.loginCount = loginCount;
        this.maxLoginCount = maxLoginCount;
        this.version = version;
        this.result = result;
    }
}