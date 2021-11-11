package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserFullNameDto;

import java.util.Set;

@Data
@QueryEntity
@Document(value = "Hairdresser")
@EqualsAndHashCode(of = {"id"})
class HairdresserDocument {

    private String id;

    private HairdresserFullNameDto fullName;

    private String photoId;

    private Set<String> serviceIds;

}
