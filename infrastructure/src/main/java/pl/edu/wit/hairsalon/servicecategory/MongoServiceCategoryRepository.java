package pl.edu.wit.hairsalon.servicecategory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoServiceCategoryRepository extends MongoRepository<ServiceCategoryDocument, String>, QuerydslPredicateExecutor<ServiceCategoryDocument> {

}
