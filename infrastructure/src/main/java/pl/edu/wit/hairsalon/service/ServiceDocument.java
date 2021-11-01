package pl.edu.wit.hairsalon.service;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.sharedkernel.dto.MoneyDto;

@Data
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Service")
@EqualsAndHashCode(of = {"id"})
class ServiceDocument {

    @Id
    private String id;

    @Indexed
    private String name;

    private MoneyDto price;

    private Integer durationInMinutes;

}
