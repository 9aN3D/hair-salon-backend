package pl.edu.wit.hairsalon.sharedKernel.document;

import com.querydsl.core.annotations.QueryEmbeddable;

@QueryEmbeddable
public record EmbeddableFullName(String name, String surname) {

}
