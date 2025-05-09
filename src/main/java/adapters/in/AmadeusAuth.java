package adapters.in;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class AmadeusAuth {
    private static final String CLIENT_ID = "niddVYEcippmf9C6iLvJLwtZLrpk2m5C";       // <- pon aquí tu client_id real
    private static final String CLIENT_SECRET = "ZjHVymTaTpCPXQtZ"; // <- y aquí tu client_secret real
    private static final String AUTH_URL = "https://test.api.amadeus.com/v1/security/oauth2/token";

    public static String getAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .build();

        Request request = new Request.Builder()
                .url(AUTH_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Error en autenticación: " + response);
            String json = response.body().string();
            return new JSONObject(json).getString("access_token");
        }
    }
}

