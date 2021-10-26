package pl.edu.wit.product.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductCategoryBaseCommand {

    private String name;

}
