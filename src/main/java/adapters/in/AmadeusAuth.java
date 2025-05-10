package adapters.in;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class AmadeusAuth {
    private static final String CLIENT_ID = "niddVYEcippmf9C6iLvJLwtZLrpk2m5C";
    private static final String CLIENT_SECRET = "ZjHVymTaTpCPXQtZ";
    private static final String AUTH_URL = "https://test.api.amadeus.com/v1/security/oauth2/token";

    private static String accessToken = null;
    private static long expirationTimeMillis = 0;

    public static String getAccessToken() throws IOException {
        long currentTime = System.currentTimeMillis();

        if (accessToken != null && currentTime < expirationTimeMillis) {
            return accessToken;
        }

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
            JSONObject jsonObject = new JSONObject(json);
            accessToken = jsonObject.getString("access_token");
            int expiresIn = jsonObject.getInt("expires_in");
            expirationTimeMillis = currentTime + (expiresIn - 60) * 1000L;
            return accessToken;
        }
    }
}


