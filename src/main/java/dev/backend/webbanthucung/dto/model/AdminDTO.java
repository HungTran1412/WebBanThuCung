package dev.backend.webbanthucung.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AdminDTO {
    Long id;
    String email;
    String img;
    String fullname;
}
