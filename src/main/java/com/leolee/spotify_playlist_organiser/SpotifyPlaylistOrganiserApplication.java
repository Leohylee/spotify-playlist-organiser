package com.leolee.spotify_playlist_organiser;

import com.leolee.spotify_playlist_organiser.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpotifyPlaylistOrganiserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyPlaylistOrganiserApplication.class, args);
	}

}
