package com.example.heygen.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Value("${heygen.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/get-token", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Map<String, String>> getToken() throws IOException {
        String url = "https://api.heygen.com/v1/streaming.create_token";

        Request request = new Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer " + apiKey)
            .post(okhttp3.RequestBody.create("", null))
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return ResponseEntity.status(response.code())
                        .body(Map.of("error", response.message()));
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.body().string());
            String token = json.get("data").get("token").asText();

            return ResponseEntity.ok(Map.of("token", token));
        }
    }
}
