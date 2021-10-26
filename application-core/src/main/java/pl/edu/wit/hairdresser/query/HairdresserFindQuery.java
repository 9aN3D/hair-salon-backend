package pl.edu.wit.hairdresser.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HairdresserFindQuery {

    private String hairdresserId;

    private String fullName;

    public static HairdresserFindQuery ofHairdresserId(String hairdresserId) {
        return HairdresserFindQuery.builder()
                .hairdresserId(hairdresserId)
                .build();
    }

}
