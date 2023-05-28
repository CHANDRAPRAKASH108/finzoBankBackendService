package com.example.finzo.service;

import com.example.finzo.entity.RoleEntity;

import java.util.List;

public interface RoleService {
    RoleEntity createRole(String roleName);
    List<RoleEntity> fetchAllRoles();
}
