package com.epam.training.microservices.songservice.songs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSongResponse {
    private String name;
    private String artist;
    private String album;
    private String length;
    private String resourceId;
    private String songYear;
}
