package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.ServiceProductMapper;
import com.lcdt.pay.model.ServiceProduct;
import com.lcdt.pay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ServiceProductMapper mapper;

    @Override
    public ServiceProduct createProduct(ServiceProduct product) {
        mapper.insert(product);
        return product;
    }

    @Override
    public List<ServiceProduct> allProduct() {
        List<ServiceProduct> serviceProducts = mapper.selectAll();
        return serviceProducts;
    }
}
