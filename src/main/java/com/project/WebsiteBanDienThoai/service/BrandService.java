package com.project.WebsiteBanDienThoai.service;
import com.project.WebsiteBanDienThoai.model.Brand;
import com.project.WebsiteBanDienThoai.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        return brand.orElse(null);
    }

    public Brand saveBrand(Brand brand){
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand brandDetails) {
        Brand brand = getBrandById(id);
        if (brand != null) {
            brand.setName(brandDetails.getName());
            return brandRepository.save(brand);
        }
        return null;
    }

    public void deleteBrand(Long id){
        brandRepository.deleteById(id);
    }
}
