package com.project.WebsiteBanDienThoai.service;
import com.project.WebsiteBanDienThoai.model.Brand;
import com.project.WebsiteBanDienThoai.repository.BrandRepository;
import jakarta.validation.constraints.NotNull;
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

    public Optional<Brand> getBrandById(Long id){
        return brandRepository.findById(id);
    }

    public Brand saveBrand(Brand brand){
        return brandRepository.save(brand);
    }

    public Brand updateBrand(@NotNull Brand brand){
        Brand existingBrand = brandRepository.findById(brand.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " + brand.getId() + " does not exist."));
        existingBrand.setName(brand.getName());
        existingBrand.setLogo(brand.getLogo());
        return brandRepository.save(existingBrand);
    }

    public void deleteBrand(Long id){
        brandRepository.deleteById(id);
    }
}
