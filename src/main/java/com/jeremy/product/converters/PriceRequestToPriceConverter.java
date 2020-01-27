package com.jeremy.product.converters;

import com.jeremy.product.models.Price;
import com.jeremy.product.models.PriceRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PriceRequestToPriceConverter implements Converter<PriceRequest, Price> {

  @Override
  public Price convert(final PriceRequest priceRequest) {
    Price price = new Price();
    price.setValue(priceRequest.getPrice().getValue());
    price.setCurrencyCode(priceRequest.getPrice().getCurrencyCode());

    return price;
  }
}
