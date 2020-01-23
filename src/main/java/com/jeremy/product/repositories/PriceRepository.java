package com.jeremy.product.repositories;

import com.jeremy.product.models.Price;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price, String> {

  Price findByProductId(String productId);
}
