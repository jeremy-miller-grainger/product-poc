package com.jeremy.product.converters;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeremy.product.models.Price;
import com.jeremy.product.models.PriceRequest;
import com.jeremy.product.models.PriceRequestValue;
import org.junit.jupiter.api.Test;

class PriceRequestToPriceConverterTest {

  private static final String VALUE = "789.00";
  private static final String CURRENCY_CODE = "USD";

  private PriceRequestToPriceConverter converter = new PriceRequestToPriceConverter();

  @Test
  void convertShouldReturnPriceForPriceRequest() {
    PriceRequestValue priceRequestValue = new PriceRequestValue();
    priceRequestValue.setValue(VALUE);
    priceRequestValue.setCurrencyCode(CURRENCY_CODE);

    PriceRequest priceRequest = new PriceRequest();
    priceRequest.setPrice(priceRequestValue);

    final Price result = this.converter.convert(priceRequest);

    assertThat(result.getValue()).isEqualTo(VALUE);
    assertThat(result.getCurrencyCode()).isEqualTo(CURRENCY_CODE);
    assertThat(result.getId()).isBlank();
    assertThat(result.getProductId()).isBlank();
  }
}