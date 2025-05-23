package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CardPage {

    private final String baseUrl = "https://api.trello.com/1";
    private final String key;
    private final String token;

    public CardPage(String key, String token) {
        this.key = key;
        this.token = token;
        RestAssured.baseURI = baseUrl;
    }

    // Kart oluşturma
    public Response createCard(String boardId, String cardName) {
        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("idList", getFirstListId(boardId)) // Board’daki ilk listenin id'si gerekiyor
                .queryParam("name", cardName)
                .when()
                .post("/cards")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    // Kart silme
    public Response deleteCard(String cardId) {
        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .delete("/cards/" + cardId)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    private String getFirstListId(String boardId) {
        Response response = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .get("/boards/" + boardId + "/lists")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response.jsonPath().getString("[0].id");
    }
    // Kart güncelleme
    public Response updateCard(String cardId, String newName) {
        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", newName)
                .when()
                .put("/cards/" + cardId)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

}
