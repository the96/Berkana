package dodontofAPI;

public class Server {
    private final String name;
    private final String url;

    public Server(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
