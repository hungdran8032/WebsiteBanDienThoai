package com.project.WebsiteBanDienThoai.service;

import com.project.WebsiteBanDienThoai.model.Role;
import com.project.WebsiteBanDienThoai.repository.IRoleRepository;
import com.project.WebsiteBanDienThoai.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional

public class RoleService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    public List<Role> getAllAccount(){
        return roleRepository.findAll();
    }
}
