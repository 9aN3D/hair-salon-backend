package pl.edu.wit.hairsalon.user;

import com.querydsl.core.annotations.QueryEmbeddable;

@QueryEmbeddable
record EmbeddableUserContact(String email) {

}
