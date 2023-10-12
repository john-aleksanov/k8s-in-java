package com.epam.training.microservices.songservice.songs;

import com.epam.training.microservices.songservice.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private static final String NOT_FOUND_TEMPLATE = "Song with ID '%s' not found.";

    private final SongRepository songRepository;
    private final ObjectMapper mapper;

    @PostMapping
    public CreateSongResponse createResource(@RequestBody CreateSongRequest request) throws Exception {
        var songEntity = mapper.readValue(mapper.writeValueAsString(request), SongEntity.class);
        var saved = songRepository.save(songEntity);
        return new CreateSongResponse(saved.getId());
    }

    @GetMapping("/{id}")
    public GetSongResponse getResource(@PathVariable int id) throws Exception {
        var song = songRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_TEMPLATE, id)));
        return mapper.readValue(mapper.writeValueAsString(song), GetSongResponse.class);
    }

    @DeleteMapping("/{ids}")
    public DeleteSongResponse deleteResource(@PathVariable List<Integer> ids) {
        songRepository.deleteAllById(ids);
        return new DeleteSongResponse(ids);
    }
}
