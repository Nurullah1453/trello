package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pages.BoardPage;
import pages.CardPage;

import java.util.Random;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardTest {

    CardPage cardPage;
    BoardPage boardPage;
    String key = "";
    String token = "";
    String boardId;
    String cardId1, cardId2;

    @BeforeAll
    public void setup() {
        boardPage = new BoardPage(key, token);
        cardPage = new CardPage(key, token);

        // Board oluşturuyoruz
        Response response = boardPage.createBoard("Test Board");
        boardId = response.jsonPath().getString("id");
    }

    @Test
    @DisplayName("Board'a 2 kart oluştur")
    public void createTwoCards() {
        Response card1 = cardPage.createCard(boardId, "Kart 1");
        cardId1 = card1.jsonPath().getString("id");
        Assertions.assertNotNull(cardId1);

        Response card2 = cardPage.createCard(boardId, "Kart 2");
        cardId2 = card2.jsonPath().getString("id");
        Assertions.assertNotNull(cardId2);

        System.out.println("Kart 1 ID: " + cardId1);
        System.out.println("Kart 2 ID: " + cardId2);
    }

    @Test
    @Order(2)
    @DisplayName("Rastgele bir kartı güncelle")
    public void updateRandomCard() {
        String targetCardId;
        String newCardName = "Güncellenmiş Kart";

        // Rastgele seçim
        if (new Random().nextBoolean()) {
            targetCardId = cardId1;
        } else {
            targetCardId = cardId2;
        }

        Response updateResponse = cardPage.updateCard(targetCardId, newCardName);
        String updatedName = updateResponse.jsonPath().getString("name");

        Assertions.assertEquals(newCardName, updatedName);
        System.out.println("Güncellenen kart ID: " + targetCardId);
    }
    @Test
    @Order(3)
    @DisplayName("Kartları sil")
    public void deleteCards() {
        Response delete1 = cardPage.deleteCard(cardId1);
        Response delete2 = cardPage.deleteCard(cardId2);

        Assertions.assertEquals(200, delete1.getStatusCode(), "1. kart silinemedi");
        Assertions.assertEquals(200, delete2.getStatusCode(), "2. kart silinemedi");

        System.out.println("Kartlar başarıyla silindi.");
    }

}
