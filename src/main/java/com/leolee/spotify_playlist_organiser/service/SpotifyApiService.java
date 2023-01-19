package com.leolee.spotify_playlist_organiser.service;

import com.leolee.spotify_playlist_organiser.model.dto.ArtistDTO;
import com.leolee.spotify_playlist_organiser.model.dto.SongDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpotifyApiService {

    private HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
    private String api_url = "https://api.spotify.com/v1";

    public String getPlaylist(String access_token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(api_url + "/me/playlists"))
                .headers("Authorization", "Bearer " + access_token)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public List<SongDTO> getPlaylistItems(String access_token, String listId) throws IOException, InterruptedException {
        String url = api_url + "/playlists/" + listId + "/tracks";
        List<SongDTO> songlist = new ArrayList<>();
        while (StringUtils.hasLength(url)) {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).headers("Authorization", "Bearer " + access_token).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonStr = Optional.ofNullable(response.body()).orElse("");
            JSONObject playlist = new JSONObject(jsonStr);
            JSONArray items = playlist.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject track = item.getJSONObject("track");
                String songId = track.isNull("id") ? "" : track.getString("id");
                String songName = track.getString("name");
                String isrc = track.getJSONObject("external_ids").has("isrc") ? track.getJSONObject("external_ids").getString("isrc") : "";
                JSONArray artistsJSON = track.getJSONArray("artists");
                ArtistDTO[] artists = new ArtistDTO[artistsJSON.length()];
                for (int j = 0; j < artistsJSON.length(); j++) {
                    JSONObject artistJSON = artistsJSON.getJSONObject(j);
                    String artistId = artistJSON.isNull("id") ? "" : artistJSON.getString("id");
                    String artistName = artistJSON.getString("name");
                    ArtistDTO artist = new ArtistDTO(artistId, artistName);
                    artists[j] = artist;
                }
                SongDTO song = new SongDTO(songId, songName, isrc, artists);
                songlist.add(song);
            }
            url = playlist.has("next") && !playlist.isNull("next") ? playlist.getString("next") : null;
        }
        return songlist;
    }

}
