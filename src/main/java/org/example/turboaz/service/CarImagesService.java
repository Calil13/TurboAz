package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.CarImageDto;
import org.example.turboaz.entity.Images;
import org.example.turboaz.exception.NotFoundException;
import org.example.turboaz.exception.UnexpectedException;
import org.example.turboaz.repository.CarImagesRepository;
import org.example.turboaz.repository.CarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarImagesService {

    @Value("${upload.path}")
    private String uploadPath;

    private final CarRepository carRepository;
    private final CarImagesRepository carImagesRepository;

    public List<CarImageDto> getCarImages(Long carId) {
        List<Images> carImage = carImagesRepository.findByCarId(carId);

        if (carImage.isEmpty()) {
            log.warn("Images not found for car with ID {}", carId);
        }

        return carImage.stream()
                .map(img -> new CarImageDto(img.getId(), img.getImageUrl()))
                .collect(Collectors.toList());
    }

    public List<CarImageDto> uploadImage(Long carId, List<MultipartFile> files) {
        var car = carRepository.findById(carId)
                .orElseThrow(() -> {
                    log.error("Car not found for images!");
                    return new NotFoundException("Car not found!");
                });

        List<Images> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    String filePath = uploadPath + File.separator + fileName;
                    file.transferTo(new File(filePath));

                    var image = new Images("/uploads/" + fileName, car);
                    savedImages.add(carImagesRepository.save(image));
                } catch (IOException e) {
                    System.err.println("Error saving file: " + file.getOriginalFilename());
                    throw new RuntimeException("File upload failed: " + file.getOriginalFilename(), e);
                }
            }
        }

        log.info("Image added for car with ID: {}", carId);
        return savedImages.stream()
                .map(img -> new CarImageDto(img.getId(), img.getImageUrl()))
                .collect(Collectors.toList());
    }

    public String deleteImage(Long id) {
        var image = carImagesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found!"));

        Path fullPath = Paths.get(uploadPath)
                .resolve(image.getImageUrl().replace("/uploads/", ""))
                .normalize();

        try {
            if (Files.exists(fullPath)) {
                Files.delete(fullPath);
            }
        } catch (Exception e) {
            log.error("An error occurred while deleting the file!");
            throw new UnexpectedException("An error occurred while deleting the file: " + fullPath);
        }

        carImagesRepository.delete(image);

        log.info("Image deleted successfully. \nID: {}", id);
        return "Image deleted successfully.";
    }
}
