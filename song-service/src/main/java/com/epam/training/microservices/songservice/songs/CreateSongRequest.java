package com.epam.training.microservices.songservice.songs;

import lombok.Getter;

@Getter
public class CreateSongRequest {
    private String name;
    private String artist;
    private String album;
    private String length;
    private String resourceId;
    private String songYear;
}
