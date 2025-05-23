package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.SSLUtil;


import static io.restassured.RestAssured.given;

public class BoardPage {

    private final String baseUrl = "https://api.trello.com/1";
    private final String key;    // Trello API Key
    private final String token;  // Trello API Token

    public BoardPage(String key, String token) {
        this.key = key;
        this.token = token;
        RestAssured.baseURI = baseUrl;

        // SSL sertifika doğrulamasını kapat (test ortamı için)
        SSLUtil.disableSSLValidation();
    }

    // Yeni board oluşturma
    public Response createBoard(String boardName) {
        return given()
                .queryParam("name", boardName)
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .post("/boards")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    // Board silme
    public Response deleteBoard(String boardId) {
        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .delete("/boards/" + boardId)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
}
