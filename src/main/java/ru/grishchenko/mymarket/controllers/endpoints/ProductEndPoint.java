package ru.grishchenko.mymarket.controllers.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.grishchenko.mymarket.services.ProductService;
import ru.grishchenko.mymarket.soap.GetAllProductsRequest;
import ru.grishchenko.mymarket.soap.GetAllProductsResponse;
import ru.grishchenko.mymarket.soap.ProductSoap;

@Endpoint
@RequiredArgsConstructor
public class ProductEndPoint {
    private static final String NAMESPACE_URI = "http://www.grishenko.ru/ws/products";
    private final ProductService productService;

    /*
        Пример запроса: POST http://localhost:8189/ws

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.grishenko.ru/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllProducts().stream().map(ProductSoap::new).forEach(response.getProducts()::add);
        return response;
    }
}
