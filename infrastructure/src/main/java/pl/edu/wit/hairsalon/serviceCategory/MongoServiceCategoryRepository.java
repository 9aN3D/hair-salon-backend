package pl.edu.wit.hairsalon.serviceCategory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoServiceCategoryRepository extends MongoRepository<ServiceCategoryDocument, String>, QuerydslPredicateExecutor<ServiceCategoryDocument> {

}
