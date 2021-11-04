package pl.edu.wit.hairsalon.member;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoMemberRepository extends MongoRepository<MemberDocument, String>, QuerydslPredicateExecutor<MemberDocument> {

}
