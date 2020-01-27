package com.jeremy.product.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeremy.product.models.PriceRequest;
import com.jeremy.product.models.PriceRequestValue;
import com.jeremy.product.models.PriceResponse;
import com.jeremy.product.models.ProductResponse;
import com.jeremy.product.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  private static final String PRODUCT_ID_TYPE_VALUE = "72456";
  private static final String TITLE = "Pink Ralph Lauren Polo Shirt, Petite Small";
  private static final String CURRENCY_CODE = "USD";
  private static final String PRICE_VALUE = "28.99";

  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Test
  void getProduct() throws Exception {
    ProductResponse productResponse = ProductResponse.builder()
        .id(PRODUCT_ID_TYPE_VALUE)
        .name(TITLE)
        .price(PriceResponse.builder().value(PRICE_VALUE).currencyCode(CURRENCY_CODE).build())
        .build();

    when(this.productService.getProduct()).thenReturn(productResponse);

    this.mockMvc.perform(get("/product/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(PRODUCT_ID_TYPE_VALUE)))
        .andExpect(jsonPath("$.name", is(TITLE)))
        .andExpect(jsonPath("$.current_price.value", is(PRICE_VALUE)))
        .andExpect(jsonPath("$.current_price.currency_code", is(CURRENCY_CODE)));
  }

  @Test
  void updateProduct() throws Exception {
    PriceRequestValue priceRequestValue = new PriceRequestValue();
    priceRequestValue.setCurrencyCode(CURRENCY_CODE);
    priceRequestValue.setValue(PRICE_VALUE);
    PriceRequest priceRequest = new PriceRequest();
    priceRequest.setPrice(priceRequestValue);
    String jsonRequestPayload = objectMapper.writeValueAsString(priceRequest);

    PriceResponse priceResponse = PriceResponse.builder()
        .currencyCode(CURRENCY_CODE)
        .value(PRICE_VALUE)
        .build();

    when(this.productService.updatePrice("1", priceRequest)).thenReturn(priceResponse);

    this.mockMvc.perform(put("/product/1")
        .content(jsonRequestPayload)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.value", is(PRICE_VALUE)))
        .andExpect(jsonPath("$.currency_code", is(CURRENCY_CODE)));
  }
}