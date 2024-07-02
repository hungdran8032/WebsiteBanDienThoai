package com.project.WebsiteBanDienThoai.service;

import com.project.WebsiteBanDienThoai.model.TypeOfProduct;
import com.project.WebsiteBanDienThoai.repository.TypeOfProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeOfProductService {
    @Autowired
    private TypeOfProductRepository typeOfProductRepository;

    public List<TypeOfProduct> getAllTypeOfProducts() {
        return typeOfProductRepository.findAll();
    }

    public Optional<TypeOfProduct> getTypeOfProductById(Long id) {
        return typeOfProductRepository.findById(id);
    }

    public TypeOfProduct saveTypeOfProduct(TypeOfProduct category) {
        return typeOfProductRepository.save(category);
    }

    public void deleteTypeOfProduct(Long id) {
        typeOfProductRepository.deleteById(id);
    }
}
