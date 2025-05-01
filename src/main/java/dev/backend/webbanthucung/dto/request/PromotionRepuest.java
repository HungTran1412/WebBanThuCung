package dev.backend.webbanthucung.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PromotionRepuest {
    String email;
    String discountCode;
    String status;
}
