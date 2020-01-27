# product-poc
Welcome to the product-poc service. This project is a proof of concept of a RESTful product API to aggregate data from multiple sources:
* Price data from a MongoDB NoSQL database
* Product data in an XML file

## Required Software
1.  [Git](https://git-scm.com/downloads) 
1.  [Gradle](https://gradle.org/install/)
1.	[Docker](http://docker.com)
1.	[Lombok](https://projectlombok.org/setup/overview)

## Building with Gradle

To start the application from the command line
```
$> ./gradlew bootRun
```

To execute the unit tests
```
$> ./gradlew test
```

You must have MongoDB installed locally and running prior to the startup of the product-poc service.
Or you can run MongoDB in a Docker container:
```
$> docker run -d -p 27017-27019:27017-27019 --name mongodb mongo:4.2.2
```

## Building with docker-compose

To run the product-poc and start the MongoDB in Docker containers:
```
$> ./gradlew clean assemble
$> docker-compose build
$> docker-compose up
```

## Using the product-poc service

### Search for a product
Once the product-poc service is running locally, you can test it with the following GET request:
```
$> curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/product/72456
```
This curl command should return the following response payload:
```json
{
  "id": "72456",
  "name": "Pink Ralph Lauren Polo Shirt, Petite Small",
  "current_price": {
    "value": "54.99",
    "currency_code": "USD"
  }
}
```
The product-poc is a proof of concept. There is only one product loaded into the pricing database at startup.
There is only one product XML request loaded. Having said that, the product id in the GET request path is ignored
and the same content is returned for each request.

### Update a product's price
You can update the price for the product using a PUT request:
```
$> curl -H "Content-Type: application/json" -d '{"current_price":{"value":"33.99","currency_code":"USD"}}' -X PUT http://localhost:8080/product/72456
```
Use the GET request above to verify that the price was updated as expected.