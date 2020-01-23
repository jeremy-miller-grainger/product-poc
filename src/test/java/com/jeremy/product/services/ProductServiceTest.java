package com.jeremy.product.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import com.jeremy.product.models.ConditionInformation;
import com.jeremy.product.models.Product;
import com.jeremy.product.models.Attribute;
import com.jeremy.product.models.ProductResponse;
import com.jeremy.product.repositories.PriceRepository;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;

class ProductServiceTest {

  private static final String SKU = "cbtsku5";
  private static final String TITLE = "Pink Ralph Lauren Polo Shirt, Petite Small";
  private static final String ATTRIBUTE_COLOR_VALUE = "Pink";
  private static final String ATTRIBUTE_SIZE_VALUE = "PS";
  private static final String ATTRIBUTE_SIZE_TYPE_VALUE = "Petites";
  private static final String PRODUCT_ID_TYPE_VALUE = "72456";
  private static final String CONDITION = "New Other";
  private static final String PICTURE_URL = "http://ebay.com/98357.JPG";

  @Mock
  private ConversionService conversionService;

  @Mock
  private PriceRepository priceRepository;

  private ProductService productService;

  @BeforeEach
  void beforeEach() {
    initMocks(this);

    productService = new ProductService(conversionService, priceRepository);
  }

  @Test
  void getProductShouldDeserializeXmlProductToJava() {
    final Product result = this.productService.getProductFromXml();

    assertThat(result.getSku()).isEqualTo(SKU);
    assertThat(result.getProductInformation().getTitle()).isEqualTo(TITLE);

    Attribute productAttributeColor = new Attribute();
    //productAttributeColor.setAttributeName(AttributeName.COLOR);
    productAttributeColor.setName("Color");
    productAttributeColor.setAttribute(ATTRIBUTE_COLOR_VALUE);
    Attribute productAttributeColor1 = new Attribute();
    //productAttributeColor1.setAttributeName(AttributeName.SIZE);
    productAttributeColor1.setName("Size (Women's)");
    productAttributeColor1.setAttribute(ATTRIBUTE_SIZE_VALUE);
    Attribute productAttributeColor2 = new Attribute();
//  productAttributeColor2.setAttributeName(AttributeName.SIZE_TYPE);
    productAttributeColor2.setName("Size Type");
    productAttributeColor2.setAttribute(ATTRIBUTE_SIZE_TYPE_VALUE);
    //assertThat(result.getProductInformation().getProductAttributes()).containsExactly(productAttributeColor, productAttributeColor1, productAttributeColor2);

    //ProductId productId = new ProductId();
    //productId.setProductIdType(ProductIdType.EPID);
    //productId.setProductIdType("EPID");
    //productId.setValue(PRODUCT_ID_TYPE_VALUE);
    //assertThat(result.getProductInformation().getProductId()).isEqualTo(productId);
    assertThat(result.getProductInformation().getProductId()).isEqualTo(PRODUCT_ID_TYPE_VALUE);

    ConditionInformation conditionInformation = new ConditionInformation();
    conditionInformation.setCondition(CONDITION);
    conditionInformation.setPictureUrl(PICTURE_URL);
    assertThat(result.getProductInformation().getConditionInformation()).isEqualTo(conditionInformation);
  }

  @Test
  void usingGuavaTest() throws IOException {
    this.productService.getProductXmlFromFile();
  }
}