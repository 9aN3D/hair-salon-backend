package pl.edu.wit.spring.adapter.persistence.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.spring.adapter.persistence.product.model.ProductDocument;

@Repository
public interface MongoProductRepository extends MongoRepository<ProductDocument, String>, QuerydslPredicateExecutor<ProductDocument> {

}
