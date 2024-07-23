package hello.hellospring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// @Builder
public class BotDto {
    private String name;
    private String version;
    private String createAt;
    private int count;
    private DetailMap detailMap;
}
