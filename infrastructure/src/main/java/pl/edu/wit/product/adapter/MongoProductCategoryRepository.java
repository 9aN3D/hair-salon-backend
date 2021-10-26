package pl.edu.wit.product.adapter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.product.adapter.model.ProductCategoryDocument;

@Repository
public interface MongoProductCategoryRepository extends MongoRepository<ProductCategoryDocument, String>, QuerydslPredicateExecutor<ProductCategoryDocument> {

}
