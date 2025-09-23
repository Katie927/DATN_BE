package com.DATN.Bej.service.identity;

import com.DATN.Bej.dto.request.identityRequest.RoleRequest;
import com.DATN.Bej.dto.response.identity.RoleResponse;
import com.DATN.Bej.mapper.RoleMapper;
import com.DATN.Bej.repository.PermissionRepository;
import com.DATN.Bej.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRResponse(role);
    }

    public List<RoleResponse> getAll(){
       return roleRepository.findAll()
               .stream()
               .map(roleMapper::toRResponse)
               .toList();
    }

    public void delete(String role){
        roleRepository.deleteById(role);
    }

}
