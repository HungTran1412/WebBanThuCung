package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.PromotionRepuest;
import dev.backend.webbanthucung.entity.Promotion;

public interface PromotionService {
    Promotion registerPromotion(PromotionRepuest request);
}
