package pl.edu.wit.application.command.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateCommand {

    private String productCategoryId;

    private String name;

    private BigDecimal price;

    private Integer durationInMinutes;

}
