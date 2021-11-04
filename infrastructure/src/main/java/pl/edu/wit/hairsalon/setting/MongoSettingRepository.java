package pl.edu.wit.hairsalon.setting;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

interface MongoSettingRepository extends MongoRepository<SettingDocument, SettingIdDto>, QuerydslPredicateExecutor<SettingDocument> {

}
