package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.models.dto.UserRegisterDto;
import com.javamentor.developer.social.platform.models.dto.UserUpdateInfoDto;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.ActiveService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Mapper(componentModel = "spring")
@Service
public abstract class UserConverter {

    protected PasswordEncoder passwordEncoder;
    protected RoleService roleService;
    protected ActiveService activeService;

    @Autowired
    protected void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    protected void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setActiveService(ActiveService activeService) {
        this.activeService = activeService;
    }

    @Mappings({
            @Mapping(target = "roleName", source = "role.name"),
            @Mapping(target = "activeName", source = "active.name")
    })
    public abstract UserDto toDto(User user);

    @Mappings({
            @Mapping(target = "role", source = "roleName", qualifiedByName = "roleSetter"),
            @Mapping(target = "active", source = "activeName", qualifiedByName = "activeSetter"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDto.getPassword()))")}
    )
    public abstract User toEntity(UserDto userDto);

    @Mappings({
            @Mapping(target = "role", constant = "USER", qualifiedByName = "roleSetter"),
            @Mapping(target = "active", constant = "ACTIVE", qualifiedByName = "activeSetter"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDto.getPassword()))")}
    )
    public abstract User toEntity(UserRegisterDto userDto);

    public abstract User toEntity(UserUpdateInfoDto userUpdateInfoDto);


    @Named("roleSetter")
    protected Role roleSetter(String role) {
        if (role == null) {
            return roleService.getByRoleName("User").get();
        }
        Optional<Role> opt = roleService.getByRoleName(role);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new EntityNotFoundException(String.format("Role с именем %s не существует", role));
        }
    }


    @Named("activeSetter")
    protected Active activeSetter(String active) {
        Optional<Active> opt = activeService.getByActiveName(active);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new EntityNotFoundException(String.format("Active с именем %s не существует", active));
        }
    }
}
