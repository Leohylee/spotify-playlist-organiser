package com.leolee.spotify_playlist_organiser.controller;

import com.leolee.spotify_playlist_organiser.model.dto.SongDTO;
import com.leolee.spotify_playlist_organiser.security.AuthorizationService;
import com.leolee.spotify_playlist_organiser.service.SpotifyApiService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SpotifyPlaylistOrganiserController {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    SpotifyApiService spotifyApiService;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(authorizationService.connect());
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state) throws IOException, InterruptedException {
        return authorizationService.callback(code, state);
    }

    @GetMapping("/playlists")
    public String playlists(@RequestParam String access_token) throws IOException, InterruptedException {
        return spotifyApiService.getPlaylist(access_token);
    }

    @GetMapping("/playlistItems")
    public List<SongDTO> playlistItems(@RequestParam String access_token, @RequestParam String listId) throws IOException, InterruptedException {
        return spotifyApiService.getPlaylistItems(access_token, listId);
    }

    @GetMapping("/error")
    public void errorHandling(HttpServletResponse response) {
        System.out.println(response);
    }

}
