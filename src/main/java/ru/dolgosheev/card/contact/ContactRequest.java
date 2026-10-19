package ru.dolgosheev.card.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(
        @NotBlank(message = "Укажите имя")
        @Size(max = 100, message = "Имя не длиннее 100 символов")
        String name,

        @NotBlank(message = "Укажите email")
        @Email(message = "Некорректный email")
        @Size(max = 200, message = "Email не длиннее 200 символов")
        String email,

        @NotBlank(message = "Напишите сообщение")
        @Size(max = 2000, message = "Сообщение не длиннее 2000 символов")
        String message
) {
}
