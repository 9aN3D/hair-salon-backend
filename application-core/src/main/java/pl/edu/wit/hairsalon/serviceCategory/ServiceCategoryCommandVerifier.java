package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;

interface ServiceCategoryCommandVerifier {

    void verify(ServiceCategoryCreateCommand command);

    void verify(ServiceCategoryUpdateCommand command);

}
