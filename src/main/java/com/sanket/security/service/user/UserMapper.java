package com.sanket.security.service.user;

import com.sanket.security.dto.user.UserDTO;
import com.sanket.security.dto.user.UsersDTO;
import com.sanket.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    UsersDTO toDTO(UserDTO userDTO);

    @Mapping(target = "userId", ignore = true)
    User toEntity(UserDTO userDTO);

    static void toEntity(final UsersDTO userDTO, final User user){
        user.setUserId(userDTO.getUserId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRoles(user.getRoles());
    }
}
