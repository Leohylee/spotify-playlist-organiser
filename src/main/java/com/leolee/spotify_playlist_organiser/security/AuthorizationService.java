package com.leolee.spotify_playlist_organiser.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leolee.spotify_playlist_organiser.model.SpotifyAccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorizationService {

    @Value("${spring.security.spotify.client_id}") private String client_id;
    @Value("${spring.security.spotify.client_secret}") private String client_secret;
    private String authorization_url = "https://accounts.spotify.com/authorize?";
    private String accessToken_url = "https://accounts.spotify.com/api/token";
    private String api_url = "https://api.spotify.com/v1";
    private String redirect_uri = "http://localhost:8080/callback";

    private HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
    private SpotifyAccessToken token = new SpotifyAccessToken();

    public String connect() {
        return authorization_url + "response_type=code" + "&client_id=" + client_id + "&redirect_uri=" + redirect_uri + "&state=" + UUID.randomUUID().toString();
    }

    public String callback(String code, String state, String instruction) throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("code", code);
        parameters.put("redirect_uri", redirect_uri);
        String form = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String beforeEncode = client_id + ":" + client_secret;
        String encodedString = Base64.getEncoder().encodeToString(beforeEncode.getBytes());
        HttpRequest request = HttpRequest.newBuilder(URI.create(accessToken_url))
                .header("Authorization", "Basic " + encodedString)
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        token = mapper.readValue(response.body(), SpotifyAccessToken.class);
        return loadBalancer(instruction);
    }

    public String loadBalancer(String instruction) throws IOException, InterruptedException {
        System.out.println(token);
        switch(instruction) {
            case "playlists":
                return getPlaylists();
            case "playlist":
                return getPlaylist();
            case "userPlaylists":
                return getUserPlaylists();
            default:
                return "test";
        }
    }

    public String getPlaylist() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(api_url + "/playlists/6a8fQpNEBtTAZ2i4PJJ6Ji"))
                .headers("Authorization", "Bearer " + token.getAccess_token())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getPlaylists() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(api_url + "/me/playlists"))
                .headers("Authorization", "Bearer " + token.getAccess_token())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getUserPlaylists() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(api_url + "/users/12156966895/playlists"))
                .headers("Authorization", "Bearer " + token.getAccess_token())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
