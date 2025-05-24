package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static groovyjarjarantlr4.v4.tool.AttributeDict.DictType.TOKEN;

public class BaseRequest {
    protected RequestSpecification request;

    private static final String BASE_URL = "";
    private static final String API_KEY = "";

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
