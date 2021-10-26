package pl.edu.wit.product.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductCategoryUpdateCommand extends ProductCategoryBaseCommand {

    public ProductCategoryUpdateCommand(String name) {
        super(name);
    }

}
