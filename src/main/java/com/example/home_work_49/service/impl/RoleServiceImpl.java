package com.example.home_work_49.service.impl;

import com.example.home_work_49.models.Role;
import com.example.home_work_49.repository.RoleRepository;
import com.example.home_work_49.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByRole(String role){
        return roleRepository.findRoleByRole(role);
    }
}
