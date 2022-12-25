package com.leolee.spotify_playlist_organiser.controller;

import com.leolee.spotify_playlist_organiser.security.AuthorizationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SpotifyPlaylistOrganiserController {

    @Autowired
    AuthorizationService service;

    private String instruction = "";

    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        instruction = "test";
        response.sendRedirect(service.connect());
    }

    @GetMapping("/playlists")
    public void playlists(HttpServletResponse response) throws IOException {
        instruction = "playlists";
        response.sendRedirect(service.connect());
    }

    @GetMapping("/playlist")
    public void playlist(HttpServletResponse response) throws IOException {
        instruction = "playlist";
        response.sendRedirect(service.connect());
    }

    @GetMapping("/userPlaylists")
    public void userPlaylists(HttpServletResponse response) throws IOException {
        instruction = "userPlaylists";
        response.sendRedirect(service.connect());
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state) throws IOException, InterruptedException {
        return service.callback(code, state, instruction);
    }

    @GetMapping("/error")
    public void errorHandling(HttpServletResponse response) {
        System.out.println(response);
    }

}
