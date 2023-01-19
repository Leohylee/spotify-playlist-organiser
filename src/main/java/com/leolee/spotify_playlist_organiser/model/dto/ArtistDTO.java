package com.leolee.spotify_playlist_organiser.model.dto;

public class ArtistDTO {

    private String id;
    private String name;

    public ArtistDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
