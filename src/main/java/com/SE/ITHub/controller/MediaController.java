package com.SE.ITHub.controller;

import com.SE.ITHub.model.Media;
import com.SE.ITHub.service.MediaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
@CrossOrigin
public class MediaController {
    private MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public List<Media> getAll() {
        return mediaService.getAllMedia();
    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Media upload(
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "altText", required = false) String altText,
            @RequestPart(value = "caption", required = false) String caption
    ) {
        return mediaService.upload(file, altText, caption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        mediaService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
