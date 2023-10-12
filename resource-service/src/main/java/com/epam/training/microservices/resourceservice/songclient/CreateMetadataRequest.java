package com.epam.training.microservices.resourceservice.songclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMetadataRequest {
    private String name;
    private String artist;
    private String album;
    private String length;

    @Setter
    private String resourceId;

    private String songYear;
}
