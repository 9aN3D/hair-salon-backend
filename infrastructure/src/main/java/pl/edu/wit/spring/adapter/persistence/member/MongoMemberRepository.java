package pl.edu.wit.spring.adapter.persistence.member;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.spring.adapter.persistence.member.model.MemberDocument;

@Repository
public interface MongoMemberRepository extends MongoRepository<MemberDocument, String>, QuerydslPredicateExecutor<MemberDocument> {

}
