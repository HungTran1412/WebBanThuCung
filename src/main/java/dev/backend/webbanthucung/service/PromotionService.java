package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.PromotionRepuest;
import dev.backend.webbanthucung.entities.Promotion;

public interface PromotionService {
    Promotion registerPromotion(PromotionRepuest request);
}
