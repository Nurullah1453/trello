package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseRequest {
    protected RequestSpecification request;

    private static final String BASE_URL = "https://api.trello.com/1";
    private static final String API_KEY = "e2f965b99c75e69715539c766a043501";
    private static final String TOKEN = "ATTA92265029651122737d4ef110df0e3683707139c1c3a715647930054e21d969a551E80F39";

    public BaseRequest() {
        request = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addQueryParam("key", API_KEY)
                .addQueryParam("token", TOKEN)
                .build();
        if (request == null) {
            System.out.println("RequestSpecification is null!");
        }
    }

}
