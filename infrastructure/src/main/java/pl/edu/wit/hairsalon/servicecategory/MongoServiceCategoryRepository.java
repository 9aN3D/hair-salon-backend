package pl.edu.wit.hairsalon.servicecategory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.servicecategory.ServiceCategoryDocument;

@Repository
interface MongoServiceCategoryRepository extends MongoRepository<ServiceCategoryDocument, String>, QuerydslPredicateExecutor<ServiceCategoryDocument> {

}
