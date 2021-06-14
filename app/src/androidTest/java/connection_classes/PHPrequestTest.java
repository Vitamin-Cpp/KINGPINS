package connection_classes;

import android.app.Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.HttpUrl;

import static org.junit.Assert.*;

public class PHPrequestTest {

    PHPrequest phPrequest = null;
    Activity activity = null;
    @Before
    public void setUp() throws Exception {
        phPrequest = new PHPrequest();
        activity = new Activity();
    }

    @Test
    public void testConnection(){
        phPrequest.doRequest(activity, "login", new RequestHandler() {
            @Override
            public HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder) {
                return null;
            }

            @Override
            public void processResponse(String responseFromRequest) {
                assert !responseFromRequest.equals("Login Successful");
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        phPrequest = null;
        activity = null;
    }
}