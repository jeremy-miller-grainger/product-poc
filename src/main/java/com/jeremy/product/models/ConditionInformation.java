package com.jeremy.product.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConditionInformation {

  private String condition;

  @JacksonXmlProperty(localName = "pictureURL")
  private String pictureUrl;
}
