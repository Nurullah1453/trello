package tests;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TrelloService {

    private final String baseUrl = "https://api.trello.com/1"; // Trello API base URL
    String apiKey;
    String token;

    public TrelloService(String apiKey, String token) {
        this.apiKey = apiKey;
        this.token = token;
    }

    // Board oluştur
    public String createBoard(String boardName) {
        HttpResponse<JsonNode> response = Unirest.post(baseUrl + "/boards/")
                .queryString("name", boardName)
                .queryString("key", apiKey)
                .queryString("token", token)
                .asJson();

        if (response.getStatus() == 200) {
            String boardId = response.getBody().getObject().getString("id");
            System.out.println("Board created with ID: " + boardId);
            return boardId;
        } else {
            System.out.println("Failed to create board. Status: " + response.getStatus());
            System.out.println("Body: " + response.getBody());
            return null;
        }
    }

    // Board içindeki default listeyi getir (ilk liste)
    public String getFirstListId(String boardId) {
        HttpResponse<JsonNode> response = Unirest.get(baseUrl + "/boards/" + boardId + "/lists")
                .queryString("key", apiKey)
                .queryString("token", token)
                .asJson();

        if (response.getStatus() == 200) {
            List<String> listIds = new ArrayList<>();
            for (int i = 0; i < response.getBody().getArray().length(); i++) {
                String listId = response.getBody().getArray().getJSONObject(i).getString("id");
                listIds.add(listId);
            }
            if (!listIds.isEmpty()) {
                System.out.println("First List ID: " + listIds.get(0));
                return listIds.get(0);
            }
        }
        System.out.println("Failed to get lists from board.");
        return null;
    }

    // Kart oluştur
    public String createCard(String listId, String cardName) {
        HttpResponse<JsonNode> response = Unirest.post(baseUrl + "/cards")
                .queryString("idList", listId)
                .queryString("name", cardName)
                .queryString("key", apiKey)
                .queryString("token", token)
                .asJson();

        if (response.getStatus() == 200) {
            String cardId = response.getBody().getObject().getString("id");
            System.out.println("Card created with ID: " + cardId);
            return cardId;
        } else {
            System.out.println("Failed to create card. Status: " + response.getStatus());
            System.out.println("Body: " + response.getBody());
            return null;
        }
    }

    // Kart güncelle
    public void updateCard(String cardId, String newName) {
        HttpResponse<JsonNode> response = Unirest.put(baseUrl + "/cards/" + cardId)
                .queryString("name", newName)
                .queryString("key", apiKey)
                .queryString("token", token)
                .asJson();

        if (response.getStatus() == 200) {
            System.out.println("Card updated successfully: " + cardId);
        } else {
            System.out.println("Failed to update card. Status: " + response.getStatus());
            System.out.println("Body: " + response.getBody());
        }
    }

    // Kart sil
    public void deleteCard(String cardId) {
        HttpResponse<JsonNode> response = Unirest.delete(baseUrl + "/cards/" + cardId)
                .queryString("key", apiKey)
                .queryString("token", token)
                .asJson();

        if (response.getStatus() == 200) {
            System.out.println("Card deleted successfully: " + cardId);
        } else {
            System.out.println("Failed to delete card. Status: " + response.getStatus());
            System.out.println("Body: " + response.getBody());
        }
    }

    // Board sil
    public void deleteBoard(String boardId) {
        HttpResponse<JsonNode> response = Unirest.delete(baseUrl + "/boards/" + boardId)
                .queryString("key", apiKey)
                .queryString("token", token)
                .asJson();

        if (response.getStatus() == 200) {
            System.out.println("Board deleted successfully: " + boardId);
        } else {
            System.out.println("Failed to delete board. Status: " + response.getStatus());
            System.out.println("Body: " + response.getBody());
        }
    }

    // MAIN - İşlemleri sırayla yap
    public static void main(String[] args) {
        // Environment variable’dan API Key ve Token al
        String apiKey = System.getenv("TRELLO_API_KEY");
        String token = System.getenv("TRELLO_TOKEN");

        if (apiKey == null || token == null) {
            System.out.println("API Key veya Token çevresel değişken olarak ayarlanmamış!");
            return;
        }

        TrelloService trello = new TrelloService(apiKey, token);

        // 1. Board oluştur
        String boardId = trello.createBoard("My Test Board");

        if (boardId == null) return;

        // 2. Board'daki ilk liste ID'sini al
        String listId = trello.getFirstListId(boardId);

        if (listId == null) return;

        // 3. İki kart oluştur
        String card1 = trello.createCard(listId, "Card One");
        String card2 = trello.createCard(listId, "Card Two");

        if (card1 == null || card2 == null) return;

        // 4. Rastgele bir kart seç ve güncelle
        Random random = new Random();
        String cardToUpdate = random.nextBoolean() ? card1 : card2;
        trello.updateCard(cardToUpdate, "Updated Card Name");

        // 5. Kartları sil
        trello.deleteCard(card1);
        trello.deleteCard(card2);

        // 6. Board'u sil
        trello.deleteBoard(boardId);
    }
}
