package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pages.BoardPage;
import pages.CardPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardTest {

    static BoardPage boardPage;
    static CardPage cardPage;

    static String boardId;
    static String cardId1;
    static String cardId2;

    @BeforeAll
    public static void setup() {
        String key = "390eb2a59167d52b1549efd7cc652927";
        String token = "d375e1412ed294aef0370d1d1eff9275f93069f33a4e7d7ffc9e23b5718e637b";
        RestAssured.useRelaxedHTTPSValidation();
        boardPage = new BoardPage(key, token);
        cardPage = new CardPage(key, token);
    }

    @Test
    @Order(1)
    @DisplayName("Board oluştur")
    public void createBoard() {
        Response response = boardPage.createBoard("TestBoard");
        boardId = response.jsonPath().getString("id");
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @Order(2)
    @DisplayName("2 kart oluştur")
    public void createCards() {
        cardId1 = cardPage.createCard(boardId, "Kart1").jsonPath().getString("id");
        cardId2 = cardPage.createCard(boardId, "Kart2").jsonPath().getString("id");
        Assertions.assertNotNull(cardId1);
        Assertions.assertNotNull(cardId2);
    }

    @Test
    @Order(3)
    @DisplayName("Rastgele kartı güncelle")
    public void updateRandomCard() {
        String cardToUpdate = Math.random() < 0.5 ? cardId1 : cardId2;
        Response updateResponse = cardPage.updateCard(cardToUpdate, "Güncellenmiş Kart");
        Assertions.assertEquals(200, updateResponse.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Kartları sil")
    public void deleteCards() {
        Response response1 = cardPage.deleteCard(cardId1);
        Response response2 = cardPage.deleteCard(cardId2);
        Assertions.assertEquals(200, response1.getStatusCode());
        Assertions.assertEquals(200, response2.getStatusCode());
    }

    @Test
    @Order(5)
    @DisplayName("Board'u sil")
    public void deleteBoard() {
        Response response = boardPage.deleteBoard(boardId);
        Assertions.assertEquals(200, response.getStatusCode());
    }
}
