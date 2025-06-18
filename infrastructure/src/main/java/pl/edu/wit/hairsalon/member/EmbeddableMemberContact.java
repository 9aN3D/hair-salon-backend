package pl.edu.wit.hairsalon.member;

import com.querydsl.core.annotations.QueryEmbeddable;

@QueryEmbeddable
record EmbeddableMemberContact(String email, String phone) {

}
