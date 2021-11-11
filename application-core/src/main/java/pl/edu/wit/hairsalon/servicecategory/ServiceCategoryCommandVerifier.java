package pl.edu.wit.hairsalon.servicecategory;

import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryUpdateCommand;

interface ServiceCategoryCommandVerifier {

    void verify(ServiceCategoryCreateCommand command);

    void verify(ServiceCategoryUpdateCommand command);

}
