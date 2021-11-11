package pl.edu.wit.hairsalon.servicecategory;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryStatusDto;

import java.util.Set;

@Data
@QueryEntity
@NoArgsConstructor
@Document(value = "ServiceCategory")
@EqualsAndHashCode(of = {"id"})
class ServiceCategoryDocument {

    @Id
    private String id;

    @Indexed
    private String name;

    private Integer order;

    private ServiceCategoryStatusDto status;

    private Set<String> serviceIds;

}
