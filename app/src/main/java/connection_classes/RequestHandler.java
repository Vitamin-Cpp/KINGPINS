package connection_classes;

import okhttp3.HttpUrl;

public interface RequestHandler {

    //method to add params to the url (e.g ..../test.php?username=kingpins)
    public abstract HttpUrl.Builder passParametersToURL(HttpUrl.Builder urlBuilder);

    //method to process json from the server
    public abstract void processResponse(String responseFromRequest);
}
