package com.lcdt.pay.service;

import com.lcdt.pay.model.ServiceProduct;

import java.util.List;

public interface ProductService {

    ServiceProduct createProduct(ServiceProduct product);

    List<ServiceProduct> allProduct();

}
