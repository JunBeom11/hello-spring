package hello.hellospring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @Builder
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "UserId cannot be null")
    private int userId;

    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @NotNull(message = "Age cannot be null")
    private int age;

    private List child;
}
