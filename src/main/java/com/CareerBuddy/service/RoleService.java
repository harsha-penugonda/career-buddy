package com.CareerBuddy.service;

import com.CareerBuddy.domain.Role;
import com.CareerBuddy.dto.RoleDTO;
import com.CareerBuddy.repository.RoleRepository;
import com.CareerBuddy.utils.transformers.RoleTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleDTO getRoleInfo(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        log.info("id: {}", id);
        ObjectId objectId = new ObjectId(id);
        Role role = roleRepository.findById(objectId).orElse(null);
        if (role == null) {
            log.info("role not found for id: {}", id);
            return null;
        }
        return RoleTransformer.transformToDTO(RoleTransformer.transformToBO(role));
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        if (roleDTO == null) {
            log.info("roleDTO is null");
            return null;
        }
        Role role = roleRepository.save(RoleTransformer.transformToDAO(RoleTransformer.transformToBO(roleDTO)));
        return RoleTransformer.transformToDTO(RoleTransformer.transformToBO(role));
    }
}
