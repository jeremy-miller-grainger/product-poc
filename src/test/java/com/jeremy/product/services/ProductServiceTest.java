package com.jeremy.product.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.jeremy.product.models.Attribute;
import com.jeremy.product.models.ConditionInformation;
import com.jeremy.product.models.Price;
import com.jeremy.product.models.PriceRequest;
import com.jeremy.product.models.PriceRequestValue;
import com.jeremy.product.models.PriceResponse;
import com.jeremy.product.models.Product;
import com.jeremy.product.models.ProductResponse;
import com.jeremy.product.repositories.PriceRepository;
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
  private static final String PRICE_VALUE = "28.99";
  private static final String CURRENCY_CODE = "USD";

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
  void getProductShouldReturnProductResponse() {
    String productId = PRODUCT_ID_TYPE_VALUE;
    Price price = new Price();
    price.setId("ID");
    price.setProductId(PRODUCT_ID_TYPE_VALUE);
    price.setValue(PRICE_VALUE);
    price.setCurrencyCode(CURRENCY_CODE);

    ProductResponse productResponse = ProductResponse.builder()
        .id(PRODUCT_ID_TYPE_VALUE)
        .name(TITLE)
        .price(PriceResponse.builder().value(PRICE_VALUE).currencyCode(CURRENCY_CODE).build())
        .build();

    when(this.priceRepository.findByProductId(productId)).thenReturn(price);
    when(this.conversionService.convert(any(), any())).thenReturn(productResponse);

    final ProductResponse result = this.productService.getProduct();

    assertThat(result.getId()).isEqualTo(PRODUCT_ID_TYPE_VALUE);
    assertThat(result.getName()).isEqualTo(TITLE);
    assertThat(result.getPrice().getValue()).isEqualTo(PRICE_VALUE);
    assertThat(result.getPrice().getCurrencyCode()).isEqualTo(CURRENCY_CODE);
  }

  @Test
  void getProductXmlFromFileShouldReturnProduct() {
    final Product result = this.productService.getProductFromXml();

    assertThat(result.getSku()).isEqualTo(SKU);
    assertThat(result.getProductInformation().getTitle()).isEqualTo(TITLE);

    Attribute productAttributeColor = new Attribute();
    productAttributeColor.setName("Color");
    productAttributeColor.setAttribute(ATTRIBUTE_COLOR_VALUE);
    Attribute productAttributeColor1 = new Attribute();
    productAttributeColor1.setName("Size (Women's)");
    productAttributeColor1.setAttribute(ATTRIBUTE_SIZE_VALUE);
    Attribute productAttributeColor2 = new Attribute();
    productAttributeColor2.setName("Size Type");
    productAttributeColor2.setAttribute(ATTRIBUTE_SIZE_TYPE_VALUE);

    assertThat(result.getProductInformation().getProductId()).isEqualTo(PRODUCT_ID_TYPE_VALUE);

    ConditionInformation conditionInformation = new ConditionInformation();
    conditionInformation.setCondition(CONDITION);
    conditionInformation.setPictureUrl(PICTURE_URL);
    assertThat(result.getProductInformation().getConditionInformation()).isEqualTo(conditionInformation);
  }

  @Test
  void updatePriceShouldReturnNullWhenNoProductExists() {
    final String id = "id";
    PriceRequestValue priceRequestValue = new PriceRequestValue();
    priceRequestValue.setCurrencyCode("USD");
    priceRequestValue.setValue("123.45");
    PriceRequest priceRequest = new PriceRequest();
    priceRequest.setPrice(priceRequestValue);

    when(this.priceRepository.findByProductId(id)).thenReturn(null);

    final PriceResponse result = this.productService.updatePrice(id, priceRequest);

    assertThat(result).isNull();
  }

  @Test
  void updatePriceShouldReturnUpdatedPriceWhenProductExists() {
    final String id = "id";
    PriceRequestValue priceRequestValue = new PriceRequestValue();
    priceRequestValue.setCurrencyCode("USD");
    priceRequestValue.setValue("123.45");
    PriceRequest priceRequest = new PriceRequest();
    priceRequest.setPrice(priceRequestValue);

    Price persistedPrice = new Price();
    persistedPrice.setCurrencyCode("CAD");
    persistedPrice.setValue("543.21");
    persistedPrice.setProductId(id);
    persistedPrice.setId("999999");

    Price convertedPriceRequest = new Price();
    convertedPriceRequest.setCurrencyCode("USD");
    convertedPriceRequest.setValue("123.45");

    PriceResponse priceResponse = PriceResponse.builder()
        .value("123.45")
        .currencyCode("USD")
        .build();

    when(this.priceRepository.findByProductId(id)).thenReturn(persistedPrice);
    when(this.conversionService.convert(priceRequest, Price.class)).thenReturn(convertedPriceRequest);
    when(this.priceRepository.save(persistedPrice)).thenReturn(persistedPrice);
    when(this.conversionService.convert(persistedPrice, PriceResponse.class)).thenReturn(priceResponse);

    final PriceResponse result = this.productService.updatePrice(id, priceRequest);

    verify(this.priceRepository).save(persistedPrice);
    assertThat(result.getCurrencyCode()).isEqualTo(priceRequest.getPrice().getCurrencyCode());
    assertThat(result.getValue()).isEqualTo(priceRequest.getPrice().getValue());
  }
}