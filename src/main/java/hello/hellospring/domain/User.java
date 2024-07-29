package hello.hellospring.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder
public class User {
    @NotNull(message = "UserId cannot be null")
    private int userId;

    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @NotNull(message = "Age cannot be null")
    private int age;
}
