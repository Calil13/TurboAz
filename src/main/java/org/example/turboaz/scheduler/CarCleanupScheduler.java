package org.example.turboaz.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.repository.CarRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CarCleanupScheduler {

    private final CarRepository carRepository;

    // Hər gecə saat 00:00-da işə düşür
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteExpiredCars() {
        log.info("Expired car cleanup started...");
        
        LocalDateTime now = LocalDateTime.now();
        
        // Vaxtı bitmiş maşınları tap və sil
        carRepository.deleteByExpirationDateBefore(now);
        
        log.info("Expired car cleanup finished.");
    }
}