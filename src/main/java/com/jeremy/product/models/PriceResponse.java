package com.jeremy.product.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE) //@Value creates an AllArgsConstructor, while this hides it preserving immutability
public class PriceResponse {

  private String value;

  @JsonProperty("currency_code")
  private String currencyCode;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PriceResponseBuilder {

  }
}
