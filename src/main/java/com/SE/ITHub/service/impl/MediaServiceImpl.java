package com.SE.ITHub.service.impl;

import com.SE.ITHub.model.Media;
import com.SE.ITHub.repository.MediaRepository;
import com.SE.ITHub.service.MediaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class MediaServiceImpl implements MediaService{

    private final S3Client s3;
    private final MediaRepository mediaRepository;

    @Value("${supabase.storage.bucket}")
    private String bucket;

    @Value("${supabase.projectUrl}")
    private String projectUrl;

    public MediaServiceImpl(S3Client s3, MediaRepository mediaRepository) {
        this.s3 = s3;
        this.mediaRepository = mediaRepository;
    }
    @Override
    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    @Override
    public Media upload(MultipartFile file, String altText, String caption) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // 1) Generate object key (unique file path inside bucket)
        String originalName = Optional.ofNullable(file.getOriginalFilename()).orElse("file");
        String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String objectKey = "uploads/" + UUID.randomUUID() + "_" + safeName;

        // 2) Upload bytes to Supabase Storage via S3 API
        try {
            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .contentType(file.getContentType())
                    .build();

            s3.putObject(putReq, RequestBody.fromBytes(file.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Supabase upload failed: " + e.getMessage(), e);
        }

        // 3) Build public URL (ONLY works if bucket is PUBLIC)
        String fileUrl = projectUrl + "/storage/v1/object/public/" + bucket + "/" + objectKey;

        // 4) Extract image width/height if it’s an image (optional)
        Integer width = null;
        Integer height = null;
        String mime = file.getContentType();

        if (mime != null && mime.startsWith("image/")) {
            try (InputStream in = file.getInputStream()) {
                BufferedImage img = ImageIO.read(in);
                if (img != null) {
                    width = img.getWidth();
                    height = img.getHeight();
                }
            } catch (Exception ignored) {}
        }

        // 5) Save DB row
        Media media = new Media();
        media.setFileName(originalName);
        media.setFileUrl(fileUrl);
        media.setMimeType(mime);
        media.setFileSizeBytes(file.getSize());
        media.setWidth(width);
        media.setHeight(height);
        media.setAltText(altText);
        media.setCaption(caption);

        media.setUpdatedAt(LocalDateTime.now());
        media.setCreatedAt(LocalDateTime.now());

        // Later: media.setUploadedBy(currentUser);

        return mediaRepository.save(media);
    }

    @Override
    public void delete(UUID id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found: " + id));

        String fileUrl = media.getFileUrl();
        System.out.println("DELETE media id=" + id);
        System.out.println("DELETE fileUrl=" + fileUrl);
        System.out.println("DELETE bucket=" + bucket);

        String objectKey = extractObjectKey(fileUrl);
        System.out.println("DELETE objectKey=" + objectKey);

        try {
            s3.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .build());
            System.out.println("DELETE storage OK");
        } catch (S3Exception e) {
            // This will print things like AccessDenied / NoSuchKey clearly
            System.out.println("DELETE storage FAILED: " + e.awsErrorDetails().errorMessage());
            System.out.println("DELETE storage code: " + e.awsErrorDetails().errorCode());
            throw e;
        }

        mediaRepository.deleteById(id);
        System.out.println("DELETE db OK");
    }

    private String extractObjectKey(String fileUrl) {
        // Your working public URL format:
        // https://<project>.supabase.co/storage/v1/object/public/SE/uploads/....

        String marker = "/object/public/" + bucket + "/";
        int idx = fileUrl.indexOf(marker);

        if (idx < 0) {
            throw new RuntimeException("Cannot extract object key. marker=" + marker + " url=" + fileUrl);
        }
        return fileUrl.substring(idx + marker.length()); // returns uploads/....
    }


}
