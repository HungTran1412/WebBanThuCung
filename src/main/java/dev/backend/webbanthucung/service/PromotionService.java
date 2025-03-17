package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.PromotionRepuest;
import dev.backend.webbanthucung.entity.Promotion;
import dev.backend.webbanthucung.repository.PromotionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    //Ham dang ki khuyen mai
    public Promotion registerPromotion(PromotionRepuest request) {
        Promotion promotion = new Promotion();

        promotion.setId(request.getId());
        promotion.setEmail(request.getEmail());

        return promotionRepository.save(promotion);
    }
}
