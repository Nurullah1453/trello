package services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class BoardService {
    private final String baseUrl = "https://api.trello.com/1";
    private String apiKey;
    private String token;

    public BoardService() {
        // Ortam değişkenlerinden al
        this.apiKey = System.getenv("ATLASSIAN_API_KEY");
        this.token = System.getenv("ATLASSIAN_TOKEN");

        if (apiKey == null || token == null) {
            System.err.println("API Key veya Token ortam değişkenlerinde bulunamadı!");
        }
    }

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
            System.out.println("Response Body: " + response.getBody());
            return null;
        }
    }
}
