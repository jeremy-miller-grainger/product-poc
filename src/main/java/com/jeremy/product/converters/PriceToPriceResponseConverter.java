package com.jeremy.product.converters;

import com.jeremy.product.models.PriceResponse;
import com.jeremy.product.models.Price;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PriceToPriceResponseConverter implements Converter<Price, PriceResponse> {

  @Override
  public PriceResponse convert(final Price price) {
    return PriceResponse.builder()
        .currencyCode(price.getCurrencyCode())
        .value(price.getValue())
        .build();
  }
}
