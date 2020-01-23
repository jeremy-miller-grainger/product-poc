package com.jeremy.product.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.io.CharStreams;
import com.jeremy.product.models.Price;
import com.jeremy.product.models.Product;
import com.jeremy.product.models.ProductResponse;
import com.jeremy.product.repositories.PriceRepository;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

  private final ConversionService conversionService;

  private final PriceRepository priceRepository;

  @Autowired
  public ProductService(ConversionService conversionService, PriceRepository priceRepository) {
    this.conversionService = conversionService;
    this.priceRepository = priceRepository;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void loadDataIntoMongoDb() {
    priceRepository.deleteAll();

    Price price = new Price();
    price.setProductId("72456");
    price.setValue("54.99");
    price.setCurrencyCode("USD");
    priceRepository.save(price);
  }

  public ProductResponse getProduct(final String productId) {
    Product product = new Product();
    Price price = new Price();

    product = this.getProductFromXml();
    price = this.getPriceForProduct(product.getProductInformation().getProductId());
    product.getProductInformation().setPrice(price);

    return conversionService.convert(product, ProductResponse.class);
  }

  Price getPriceForProduct(final String productId) {

    return priceRepository.findByProductId(productId);
  }

  Product getProductFromXml() {
    Product product = new Product();

    try {
      String xml = getProductXmlFromFile();

      XmlMapper xmlMapper = new XmlMapper();
      product = xmlMapper.readValue(xml, Product.class);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    return product;
  }

  String getProductXmlFromFile() throws IOException {

    InputStream inputStream = new ClassPathResource("products.xml").getInputStream();

    String xml = "";
    try (final Reader reader = new InputStreamReader(inputStream)) {
      xml = CharStreams.toString(reader);
    }
    return xml;
  }
}
