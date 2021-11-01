package pl.edu.wit.hairsalon.servicecategory;

import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryStatusDto;

enum ServiceCategoryStatus {

    ACTIVE, INACTIVE;

    ServiceCategoryStatusDto toDto() {
        return ServiceCategoryStatusDto.valueOf(this.name());
    }

    static ServiceCategoryStatus valueOf(ServiceCategoryStatusDto statusDto) {
        return ServiceCategoryStatus.valueOf(statusDto.name());
    }

}
