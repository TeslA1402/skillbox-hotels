package org.example.skillboxhotels.mapper;

import org.example.skillboxhotels.controller.request.UserRequest;
import org.example.skillboxhotels.controller.response.UserResponse;
import org.example.skillboxhotels.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface UserMapper extends CommonMapper {
    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "username", qualifiedByName = "trim")
    @Mapping(target = "email", qualifiedByName = "normalizeEmail")
    User toUser(UserRequest userRequest, String encodedPassword);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(User newUser, @MappingTarget User user);
}