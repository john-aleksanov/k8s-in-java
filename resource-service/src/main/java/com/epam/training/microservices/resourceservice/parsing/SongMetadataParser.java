package com.epam.training.microservices.resourceservice.parsing;

import com.epam.training.microservices.resourceservice.songclient.CreateMetadataRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class SongMetadataParser {

    public CreateMetadataRequest parseMetadata(InputStream content) throws Exception {
        var handler = new BodyContentHandler();
        var metadata = new Metadata();
        var parseContext = new ParseContext();
        var mp3Parser = new Mp3Parser();
        mp3Parser.parse(content, handler, metadata, parseContext);
        return buildSongServiceRequest(metadata);
    }

    private CreateMetadataRequest buildSongServiceRequest(Metadata metadata) {
        return CreateMetadataRequest.builder()
            .songYear(metadata.get("xmpDM:releaseDate"))
            .length(metadata.get("xmpDM:duration"))
            .album(metadata.get("xmpDM:album"))
            .artist(metadata.get("xmpDM:artist"))
            .name(metadata.get("title"))
            .build();
    }
}
