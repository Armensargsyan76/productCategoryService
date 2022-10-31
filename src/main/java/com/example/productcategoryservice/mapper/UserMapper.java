package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.UserAuthDto;
import com.example.productcategoryservice.dto.UserLoginRequestDto;
import com.example.productcategoryservice.dto.UserProductDetailDto;
import com.example.productcategoryservice.dto.UserRegisterDto;
import com.example.productcategoryservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(UserRegisterDto userRegisterDto);

    User map(UserLoginRequestDto userLoginDto);

    UserAuthDto map(User user);

    User map(UserProductDetailDto userProductDetailDto);

}
