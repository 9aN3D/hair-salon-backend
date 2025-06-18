package pl.edu.wit.hairsalon.sharedKernel.document;

import com.querydsl.core.annotations.QueryEmbeddable;

import java.time.LocalDateTime;

@QueryEmbeddable
public record EmbeddableDateRange(LocalDateTime start, LocalDateTime end) {

}
