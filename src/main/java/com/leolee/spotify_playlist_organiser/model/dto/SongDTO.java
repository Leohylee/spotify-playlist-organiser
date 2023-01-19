package com.leolee.spotify_playlist_organiser.model.dto;

public class SongDTO {

    private String id;
    private String name;
    private String isrc;
    private ArtistDTO[] artists;

    public SongDTO(String id, String name, String isrc, ArtistDTO[] artists) {
        this.id = id;
        this.name = name;
        this.isrc = isrc;
        this.artists = artists;
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

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public ArtistDTO[] getArtists() {
        return artists;
    }

    public void setArtists(ArtistDTO[] artists) {
        this.artists = artists;
    }
}
