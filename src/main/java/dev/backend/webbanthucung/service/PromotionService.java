package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.PromotionRepuest;
import dev.backend.webbanthucung.entity.Promotion;
import dev.backend.webbanthucung.repository.PromotionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    static String generatePromotionCode(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder promotionCode = new StringBuilder();
        Random rnd = new Random();

        for(int i = 0; i < 8; i++){
            promotionCode.append(chars.charAt(rnd.nextInt(chars.length())));
        }

        return promotionCode.toString();
    }

    //Ham dang ki khuyen mai
    public Promotion registerPromotion(PromotionRepuest request) {
        Promotion promotion = new Promotion();

        promotion.setEmail(request.getEmail());
        promotion.setDiscountCode(generatePromotionCode());
        promotion.setStatus("AVAIABLE");

        return promotionRepository.save(promotion);
    }

//    public static void main(String[] args) {
//        String str =  generatePromotionCode();
//        System.out.println(str);
//    }
}
