package pl.edu.wit.member.adapter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.member.adapter.model.MemberDocument;

@Repository
public interface MongoMemberRepository extends MongoRepository<MemberDocument, String>, QuerydslPredicateExecutor<MemberDocument> {

}
