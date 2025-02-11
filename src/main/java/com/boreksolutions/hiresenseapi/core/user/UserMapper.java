package com.boreksolutions.hiresenseapi.core.user;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import com.boreksolutions.hiresenseapi.core.user.dto.response.UserDto;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //@Mapping(target = "isEnabled", expression = "java(true)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    User dtoToEntity(CreateUser createUser);

    @Mapping(target = "data", source = "content")
    @Mapping(target = "totalSize", expression = "java(page.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "size", expression = "java(page.getSize())")
    @Mapping(target = "page", expression = "java(page.getNumber())")
    PageObject<UserDto> pageToPageObject(Page<User> page);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateDtoToEntity(UserDto source, @MappingTarget User target);

    UserDto entityToDto(User user);
}