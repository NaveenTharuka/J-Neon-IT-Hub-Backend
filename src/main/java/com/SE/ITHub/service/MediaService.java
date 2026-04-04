package com.SE.ITHub.service;

import com.SE.ITHub.model.Media;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MediaService {
    public Media upload(MultipartFile file, String altText,  String caption);
    public void delete(UUID mediaId);
    public List<Media> getAllMedia();
}
