package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.PromotionRepuest;
import dev.backend.webbanthucung.entity.Promotion;
import dev.backend.webbanthucung.service.PromotionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PromotionController {
    @Autowired
    PromotionService promotionService;

    //Dang ki khuyen mai
    @PostMapping("/promotion")
    Promotion registerPromotion(@RequestBody PromotionRepuest request) {
        return promotionService.registerPromotion(request);
    }
}
