package com.jeremy.product.converters;

import com.jeremy.product.models.Product;
import com.jeremy.product.models.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductResponseConverter implements Converter<Product, ProductResponse> {

  private final PriceToPriceResponseConverter priceToPriceResponseConverter;

  @Autowired
  public ProductToProductResponseConverter (PriceToPriceResponseConverter priceToPriceResponseConverter) {
    this.priceToPriceResponseConverter = priceToPriceResponseConverter;
  }

  @Override
  public ProductResponse convert(final Product product) {
    return ProductResponse.builder()
        .id(product.getProductInformation().getProductId())
        .name(product.getProductInformation().getTitle())
        .price(product.getProductInformation().getPrice() == null ? null : priceToPriceResponseConverter.convert(product.getProductInformation().getPrice()))
        .build();
  }
}
