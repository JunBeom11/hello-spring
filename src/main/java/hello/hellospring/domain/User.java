package hello.hellospring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// @Builder
public class User {
    private int userId;
    private String userName;
    private int age;
}
