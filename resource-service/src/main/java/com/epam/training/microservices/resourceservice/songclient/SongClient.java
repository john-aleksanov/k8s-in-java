package com.epam.training.microservices.resourceservice.songclient;

import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class SongClient {

    private static final String ENDPOINT_TEMPLATE = "http://%s:%s/songs";

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    public CreateMetadataResponse postSongMetadata(CreateMetadataRequest request) {
        var songServiceEurekaInstance = eurekaClient.getApplication("song-service").getInstances().get(0);
        var endpoint = String.format(ENDPOINT_TEMPLATE, songServiceEurekaInstance.getIPAddr(),
            songServiceEurekaInstance.getPort());
        return restTemplate.postForObject(URI.create(endpoint), request,
            CreateMetadataResponse.class);
    }
}
