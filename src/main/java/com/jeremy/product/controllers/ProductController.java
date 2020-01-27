package com.jeremy.product.controllers;

import com.jeremy.product.models.PriceRequest;
import com.jeremy.product.models.PriceResponse;
import com.jeremy.product.models.ProductResponse;
import com.jeremy.product.services.ProductService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/product" )
@Validated
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ProductResponse getProduct(@PathVariable @NotBlank @Size(max = 10) final String id) {
    return this.productService.getProduct();
  }

  @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public PriceResponse updatePrice(@PathVariable @NotBlank @Size(max = 10) final String id, @Valid @RequestBody PriceRequest priceRequest) {
    return this.productService.updatePrice(id, priceRequest);
  }
}
