package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryStatusDto;

enum ServiceCategoryStatus {

    ACTIVE, INACTIVE;

    ServiceCategoryStatusDto toDto() {
        return ServiceCategoryStatusDto.valueOf(this.name());
    }

    static ServiceCategoryStatus valueOf(ServiceCategoryStatusDto statusDto) {
        return ServiceCategoryStatus.valueOf(statusDto.name());
    }

}
