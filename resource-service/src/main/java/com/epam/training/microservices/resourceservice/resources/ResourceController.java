package com.epam.training.microservices.resourceservice.resources;

import com.epam.training.microservices.resourceservice.exceptions.NotFoundException;
import com.epam.training.microservices.resourceservice.parsing.SongMetadataParser;
import com.epam.training.microservices.resourceservice.resources.persistence.ResourceEntity;
import com.epam.training.microservices.resourceservice.resources.persistence.ResourceRepository;
import com.epam.training.microservices.resourceservice.resources.validation.ValidMp3;
import com.epam.training.microservices.resourceservice.songclient.SongClient;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private static final String NOT_FOUND_TEMPLATE = "Resource with ID '%s' not found.";

    private final ResourceRepository resourceRepository;
    private final SongMetadataParser metadataParser;
    private final SongClient songClient;

    @PostMapping(consumes = "multipart/form-data")
    public CreateResourceResponse createResource(@RequestParam("file") @ValidMp3 MultipartFile file) throws Exception {
        var saved = resourceRepository.save(new ResourceEntity(file.getBytes()));
        var createMetadataRequest = metadataParser.parseMetadata(file.getInputStream());
        createMetadataRequest.setResourceId(String.valueOf(saved.getId()));
        songClient.postSongMetadata(createMetadataRequest);
        return new CreateResourceResponse(saved.getId());
    }

    @GetMapping("/{id}")
    public Resource getResource(@PathVariable int id) {
        var resource = resourceRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_TEMPLATE, id)));
        return new ByteArrayResource(resource.getContent());
    }

    @DeleteMapping("/{ids}")
    public DeleteResourceResponse deleteResource(
        @PathVariable
        @Length(min = 1, max = 200, message = "The ids string should be less than 200 characters")
        @Pattern(regexp = "^\\d+(,\\d+)*$", message = "IDs should be a valid CSV") String ids) {
        var idList = Arrays.stream(ids.split(","))
            .map(Integer::valueOf)
            .toList();
        resourceRepository.deleteAllById(idList);
        return new DeleteResourceResponse(idList);
    }
}
