package com.epam.training.microservices.resourceservice.songclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class SongClient {

    private static final String SONG_SERVICE_URL = "http://song-service:8086/songs";

    private final RestTemplate restTemplate;

    public CreateMetadataResponse postSongMetadata(CreateMetadataRequest request) {
        return restTemplate.postForObject(URI.create(SONG_SERVICE_URL), request, CreateMetadataResponse.class);
    }
}
