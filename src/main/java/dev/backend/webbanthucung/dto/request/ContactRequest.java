package dev.backend.webbanthucung.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactRequest {
    final int lenghtOfPhoneNumber = 10;
    String fullName;
    String email;

    //Định nghĩa kích thước của số điện thoại
    @Size(min = lenghtOfPhoneNumber, message = "Phone number must be at least 10 characters")
    String phone;
    String content;
    LocalDate date;
    String address;
}
