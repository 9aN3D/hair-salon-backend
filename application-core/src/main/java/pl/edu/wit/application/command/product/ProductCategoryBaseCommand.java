package pl.edu.wit.application.command.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductCategoryBaseCommand {

    private String name;

}
