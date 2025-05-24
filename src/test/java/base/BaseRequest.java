package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseRequest {
    protected RequestSpecification request;

    private static final String BASE_URL = ""; // Bunu sen kendine göre doldur

    public BaseRequest() {
        // Ortam değişkenlerinden al
        String apiKey = System.getenv("ATLASSIAN_API_KEY");
        String token = System.getenv("ATLASSIAN_TOKEN");

        if (apiKey == null || token == null) {
            System.err.println("API Key veya Token ortam değişkenlerinde bulunamadı!");
            // İstersen hata fırlatabilir ya da default değer atayabilirsin
        }

        request = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addQueryParam("key", apiKey)
                .addQueryParam("token", token)
                .build();

        if (request == null) {
            System.out.println("RequestSpecification is null!");
        }
    }
}
