package pl.edu.wit.hairsalon.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoServiceRepository extends MongoRepository<ServiceDocument, String>, QuerydslPredicateExecutor<ServiceDocument> {

}
