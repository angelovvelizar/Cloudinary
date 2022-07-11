package com.example.cloudinaryapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    CloudinaryImage upload(MultipartFile file);

    boolean delete(String publicId);
}
