package hello.hellospring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder
public class User {
    private int userId;
    private String userName;
    private int age;
}
