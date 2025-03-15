package com.epay.mapper;

import com.epay.dto.UserServicesDTO;
import com.epay.entity.UserServices;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// MapStruct Mapper Interface
@Mapper(componentModel = "spring")
public interface UserServicesMapper {

    UserServicesMapper INSTANCE = Mappers.getMapper(UserServicesMapper.class);

    // Map UserServices entity to UserServicesDTO
    UserServicesDTO toDTO(UserServices userServices);

    // Map UserServicesDTO to UserServices entity
    UserServices toEntity(UserServicesDTO userServicesDTO);
}
