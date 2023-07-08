package com.example.finzo.controller;

import com.example.finzo.entity.RoleEntity;
import com.example.finzo.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finzo/roles")
public class RolesController {

    @Autowired
    RoleService roleService;
    @PostMapping("/{roleName}")
    public ResponseEntity<RoleEntity> createRoles(@PathVariable String roleName) {
        RoleEntity role = this.roleService.createRole(roleName);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @GetMapping("/allRoles")
    public ResponseEntity<List<RoleEntity>> fetchAllRoles(){
        List<RoleEntity> roleEntities = this.roleService.fetchAllRoles();
        return new ResponseEntity<>(roleEntities, HttpStatus.OK);
    }
}
