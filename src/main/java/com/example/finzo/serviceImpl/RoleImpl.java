package com.example.finzo.serviceImpl;

import com.example.finzo.Exception.ResourceNotFoundException;
import com.example.finzo.Repository.RoleRepo;
import com.example.finzo.entity.RoleEntity;
import com.example.finzo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class RoleImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;
    @Override
    public RoleEntity createRole(String roleName) {
        RoleEntity role = new RoleEntity();
        role.setRoleName(roleName);
        return roleRepo.save(role);
    }

    public String fetchRoleById(Integer id){
        RoleEntity role = roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return role.getRoleName();
    }

    @Override
    public List<RoleEntity> fetchAllRoles() {
        return roleRepo.findAll();
    }
}
