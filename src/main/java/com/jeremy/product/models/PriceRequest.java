package com.jeremy.product.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PriceRequest {

  @JsonProperty("current_price")
  @NotNull(message = "validation.currentprice.required")
  @Valid
  private PriceRequestValue price;
}
