package hello.hellospring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DetailMap {
    private int detailId;
    private String detailName;
    private List<User> userList; // UserList 추가
}
