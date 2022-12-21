package com.leolee.spotify_playlist_organiser.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Value("${spring.security.spotify.client_id}") private String client_id;
    @Value("${spring.security.spotify.client_secret}") private String client_secret;
    private String url = "https://accounts.spotify.com/api/token";

    public void connect() {
        System.out.println(client_id);
    }

}
