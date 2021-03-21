package ru.grishchenko.mymarket.soap;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "products"
})
@XmlRootElement(name = "getAllProductsResponse")
public class GetAllProductsResponse {

    @XmlElement(required = true)
    protected List<ProductSoap> products;

    public List<ProductSoap> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }
}
