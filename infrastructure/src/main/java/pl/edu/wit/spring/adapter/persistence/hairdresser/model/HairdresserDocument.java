package pl.edu.wit.spring.adapter.persistence.hairdresser.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@QueryEntity
@Document(value = "Hairdresser")
@EqualsAndHashCode(of = {"id"})
public class HairdresserDocument {

    private String id;

    private String name;

    private String surname;

    private String photoId;

    private Set<String> services;

}
