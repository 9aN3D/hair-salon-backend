package pl.edu.wit.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductCategoryConciseResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

}
