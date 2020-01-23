package com.jeremy.product.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInformation {

  @JacksonXmlProperty(localName = "localizedFor", isAttribute = true)
  private String localizedFor;

  @JacksonXmlProperty(localName = "title")
  private String title;

  private Description description;

  @JacksonXmlProperty(localName = "attribute")
  @JacksonXmlElementWrapper(useWrapping = false)
  private List<Attribute> productAttributes;

  @JacksonXmlProperty(localName = "productID")
  private String productId;

  @JacksonXmlProperty(localName = "conditionInfo")
  private ConditionInformation conditionInformation;

  private Price price;
}
