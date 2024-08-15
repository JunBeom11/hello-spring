package hello.hellospring.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamService {

    @Test
    void useStream() {
        List<Map> list = getList();

        Map<String, List<Map>> collect = list.stream()
                .filter(map -> map.get("groupCode") != null)
                .collect(Collectors.groupingBy(map -> (String) map.get("groupCode")));

        List<PhoneNumberGroup> collect1 = collect.entrySet().stream()
                .map(entry -> {
                    String groupCode = entry.getKey();
                    List<Map> maps = entry.getValue();
                    String groupName = maps.stream()
                            .map(map -> (String) map.get("groupName"))
                            .filter(Objects::nonNull)
                            .findFirst()
                            .orElse("미 그룹");

                    List<String> phoneNumbers = maps.stream()
                            .map(map -> (String) map.get("phoneNumber"))
                            .collect(Collectors.toList());

                    return PhoneNumberGroup.builder()
                            .groupCode(groupCode)
                            .groupName(groupName)
                            .isGroup(!Objects.equals(groupCode, "0000"))
                            .number(phoneNumbers)
                            .build();
                })
                .toList();

        collect1.forEach(group -> {
            System.out.println("Group Code: " + group.getGroupCode());
            System.out.println("Group Name: " + group.getGroupName());
            System.out.println("Phone Numbers: " + group.getNumber());
            System.out.println();
        });

    }

    @NotNull
    private List<Map> getList() {
        List<Map> list = new ArrayList<>();

        Map map = new LinkedHashMap();
        map.put("groupCode", "Agroup");
        map.put("groupName", "에이그룹");
        map.put("phoneNumber", "001012345678");

        Map map2 = new LinkedHashMap();
        map2.put("groupCode", "Agroup");
        map2.put("groupName", "에이그룹");
        map2.put("phoneNumber", "001022223333");

        Map map3 = new LinkedHashMap();
        map3.put("groupCode", "0000");
        map3.put("groupName", "미 그룹");
        map3.put("phoneNumber", "001099998888");

        list.add(map);
        list.add(map2);
        list.add(map3);

        return list;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class PhoneNumberGroup {
        private String groupCode;
        private String groupName;
        private Boolean isGroup = false;
        private List<String> number = new ArrayList<>();
    }

}
