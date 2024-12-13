package com.smartjob.users.mapper;

import com.smartjob.users.model.dto.user.PhoneDto;
import com.smartjob.users.model.dto.user.UserRequestDto;
import com.smartjob.users.model.dto.user.UserResponseDto;
import com.smartjob.users.model.entity.Phone;
import com.smartjob.users.model.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface defining mappings between User entities, and their corresponding DTO representations (Data Transfer Objects).
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    /**
     * Singleton instance of the mapper.
     */
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    /**
     * Converts a User entity to a UserResponseDto object,
     * mapping only specified fields.
     *
     * @param source The User entity to be converted.
     * @return The corresponding UserResponseDto object.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "modified", target = "modified")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "isActive", target = "isActive")
    UserResponseDto toUserResponseDto(User source);

    /**
     * Converts a UserRequestDto object to a User entity,
     * mapping only specified fields.
     *
     * @param source The UserRequestDto object to be converted.
     * @return The corresponding User entity.
     */
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phones", target = "phones")
    User fromUserRequestDto(UserRequestDto source);

    /**
     * Converts a PhoneDto object to a Phone entity,
     * mapping only specified fields.
     *
     * @param source The PhoneDto object to be converted.
     * @return The corresponding Phone entity.
     */
    @Mapping(source = "number", target = "number")
    @Mapping(source = "cityCode", target = "cityCode")
    @Mapping(source = "countryCode", target = "countryCode")
    Phone fromPhoneDto(PhoneDto source);

    /**
     * Converts a list of User entities to a list of UserResponseDto objects,
     * delegating the conversion for each User entity individually.
     *
     * @param source The list of User entities to be converted.
     * @return The corresponding list of UserResponseDto objects.
     */
    List<UserResponseDto> userListToUserResponseDtoList(List<User> source);

    /**
     * A callback method invoked after mapping a User entity.
     * Sets the User reference for each Phone within the User's phone list.
     * (This is necessary due to limitations in MapStruct handling bidirectional relationships.)
     *
     * @param user The User entity that was just mapped.
     */
    @AfterMapping
    default void afterMappingUser(@MappingTarget User user) {
        user.getPhones().forEach(x -> x.setUser(user));
    }
}
