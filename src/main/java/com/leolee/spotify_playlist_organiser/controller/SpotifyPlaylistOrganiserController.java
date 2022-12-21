package com.leolee.spotify_playlist_organiser.controller;

import com.leolee.spotify_playlist_organiser.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SpotifyPlaylistOrganiserController {

    @Autowired
    AuthorizationService service;

    @GetMapping("/test")
    public String test() {
        service.connect();
        return "testing";
    }

}
