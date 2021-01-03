package pl.edu.wit.spring.adapter.repository.member;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wit.spring.adapter.repository.member.document.MemberDocument;

public interface MongoMemberRepository extends MongoRepository<MemberDocument, String> {

}
