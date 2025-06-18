package pl.edu.wit.hairsalon.sharedKernel.dto;

public record FullNameDto(String name, String surname) {

    @Override
    public String toString() {
        return name + ' ' + surname;
    }

}
