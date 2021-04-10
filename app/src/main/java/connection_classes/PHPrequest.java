package connection_classes;

import android.app.Activity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import constants_classes.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PHPrequest {

    public void doRequest(final Activity activity, String phpFileOnServer, final RequestHandler handler)
    {

        //building url using HttpUrl class
        String url=handler.passParametersToURL(HttpUrl.parse(Constants.URL_LINK +phpFileOnServer)
                .newBuilder()).build().toString();

        //putting a client in place for server-app communication
        OkHttpClient client =new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        //making the call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);
                }else{
                    final String jsonResponse=response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.processResponse(jsonResponse);
                        }
                    });
                }

            }
        });
    }
}
