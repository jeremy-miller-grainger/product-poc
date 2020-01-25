package com.jeremy.product.converters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.jeremy.product.models.Attribute;
import com.jeremy.product.models.ConditionInformation;
import com.jeremy.product.models.Description;
import com.jeremy.product.models.Price;
import com.jeremy.product.models.Product;
import com.jeremy.product.models.ProductInformation;
import com.jeremy.product.models.ProductResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

class ProductToProductResponseConverterTest {

  private static final String ATTRIBUTE = "ATTRIBUTE";
  private static final String CONDITION = "CONDITION";
  private static final String CURRENCY_CODE = "CURRENCY_CODE";
  private static final String ID = "ID";
  private static final String LOCALIZED_FOR = "LOCALIZED_FOR";
  private static final String NAME = "NAME";
  private static final String PICTURE_URL = "PICTURE_URL";
  private static final String PRICE_PRODUCT_ID = "PRICE_PRODUCT_ID";
  private static final String PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION";
  private static final String PRODUCT_ID = "PRODUCT_ID";
  private static final String SKU = "SKU";
  private static final String TITLE = "TITLE";
  private static final String VALUE = "VALUE";

  private final PriceToPriceResponseConverter priceToPriceResponseConverter = new PriceToPriceResponseConverter();

  private final ProductToProductResponseConverter productToProductResponseConverter = new ProductToProductResponseConverter(priceToPriceResponseConverter);

  @Test
  void convertShouldCreateProductResponseFromProduct() {
    Product product = new Product();
    product.setSku(SKU);
    product.setProductInformation(createProductInformation());

    final ProductResponse result = productToProductResponseConverter.convert(product);

    assertThat(result.getId()).isEqualTo(PRODUCT_ID);
    assertThat(result.getName()).isEqualTo(TITLE);
    assertThat(result.getPrice().getValue()).isEqualTo(VALUE);
    assertThat(result.getPrice().getCurrencyCode()).isEqualTo(CURRENCY_CODE);
  }

  private ProductInformation createProductInformation() {
    ProductInformation productInformation = new ProductInformation();

    Price price = new Price();
    price.setId(ID);
    price.setProductId(PRICE_PRODUCT_ID);
    price.setValue(VALUE);
    price.setCurrencyCode(CURRENCY_CODE);
    productInformation.setPrice(price);

    ConditionInformation conditionInformation = new ConditionInformation();
    conditionInformation.setCondition(CONDITION);
    conditionInformation.setPictureUrl(PICTURE_URL);
    productInformation.setConditionInformation(conditionInformation);

    Description description = new Description();
    description.setProductDescription(PRODUCT_DESCRIPTION);
    productInformation.setDescription(description);

    Attribute attribute = new Attribute();
    attribute.setAttribute(ATTRIBUTE);
    attribute.setName(NAME);
    productInformation.setProductAttributes(List.of(attribute));

    productInformation.setLocalizedFor(LOCALIZED_FOR);
    productInformation.setProductId(PRODUCT_ID);
    productInformation.setTitle(TITLE);

    return productInformation;
  }
}