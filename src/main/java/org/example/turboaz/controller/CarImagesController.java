package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.CarImageDto;
import org.example.turboaz.service.CarImagesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carImages")
public class CarImagesController {

    private final CarImagesService carImagesService;

    @GetMapping("/{carId}")
    public List<CarImageDto> getCarImages(@PathVariable Long carId) {
        return carImagesService.getCarImages(carId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "{carId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<CarImageDto> uploadImage(@PathVariable Long carId, @RequestPart("files") List<MultipartFile> files) {
        return carImagesService.uploadImage(carId, files);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable Long id) {
        return carImagesService.deleteImage(id);
    }
}
