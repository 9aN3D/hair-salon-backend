package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.annotations.QueryEmbeddable;

@QueryEmbeddable
record EmbeddableHairdresserFullName(String name, String surname) {

}
