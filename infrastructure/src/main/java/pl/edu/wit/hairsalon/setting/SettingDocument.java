package pl.edu.wit.hairsalon.setting;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

@Data
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Setting")
@EqualsAndHashCode(of = {"id"})
class SettingDocument {

    @Id
    private SettingIdDto id;

    private String value;

}
