package com.jeremy.product.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "product")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

  @JacksonXmlProperty(localName = "SKU")
  private String sku;

  @JacksonXmlProperty(localName = "productInformation")
  private ProductInformation productInformation;
}
