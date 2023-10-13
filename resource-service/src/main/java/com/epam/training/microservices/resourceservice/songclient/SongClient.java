package com.epam.training.microservices.resourceservice.songclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class SongClient {

    private final RestTemplate restTemplate;

    public CreateMetadataResponse postSongMetadata(CreateMetadataRequest request) {
        return restTemplate.postForObject(URI.create("http://song-service:8086/songs"), request,
            CreateMetadataResponse.class);
    }
}
