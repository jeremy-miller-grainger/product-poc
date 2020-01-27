package com.jeremy.product.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PriceRequestValue {

  @NotBlank(message = "validation.value.required")
  private String value;

  @JsonProperty("currency_code")
  @NotBlank(message = "validation.currencycode.required")
  private String currencyCode;
}
