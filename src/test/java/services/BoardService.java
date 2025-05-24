package services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class BoardService {
    private final String baseUrl = "https://api.trello.com/1";
    private final String apiKey;
    private final String token;

    public BoardService(String apiKey, String token) {
        this.apiKey = apiKey;
        this.token = token;
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
            System.out.println("Body: " + response.getBody());
            return null;
        }
    }
}
