package com.jeremy.product.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Price {

  @Id
  private String id;

  private String productId;

  private String value;

  private String currencyCode;
}

