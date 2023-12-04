package fossid.componentUrlChecker.values;

import java.io.Serializable;

public class LoginValues implements Serializable {
    private static final LoginValues values = new LoginValues();

    public LoginValues() {
    }

    public static LoginValues getInstance() {
        return values;
    }

    private String serverUri;
    private static String serverApiUri;
    private static String username;
    private static String apikey;

    public String getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public String getServerApiUri() {
        return serverApiUri;
    }

    public void setServerApiUri(String serverApiUri) {
        LoginValues.serverApiUri = serverApiUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        LoginValues.username = username;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        LoginValues.apikey = apikey;
    }
}
