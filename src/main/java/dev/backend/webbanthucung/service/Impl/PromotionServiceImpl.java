package dev.backend.webbanthucung.service.Impl;

import dev.backend.webbanthucung.dto.request.PromotionRepuest;
import dev.backend.webbanthucung.entities.Promotion;
import dev.backend.webbanthucung.repository.PromotionRepository;
import dev.backend.webbanthucung.service.EmailService;
import dev.backend.webbanthucung.service.PromotionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    EmailService sendEmail;

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

        String strCode = generatePromotionCode();

        promotion.setEmail(request.getEmail());
        promotion.setDiscountCode(strCode);
        promotion.setStatus("AVAIABLE");

        String subject = "Đăng ký khuyến mãi thành công!";
        String body = "Mã khuyến mãi: " + strCode
                + "\nCảm ơn quý khách đã sử dụng dịch vụ của chúng tôi";

        sendEmail.sendMail(request.getEmail(), subject, body);

        return promotionRepository.save(promotion);
    }

//    public static void main(String[] args) {
//        String str =  generatePromotionCode();
//        System.out.println(str);
//    }
}
