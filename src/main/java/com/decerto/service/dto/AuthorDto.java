package com.decerto.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("author")
public class AuthorDto {

    @ApiModelProperty(value = "Author name", position = 1, example = "Grzegorz")
    String name;

    @ApiModelProperty(value = "Author surname", position = 2, example = "Brzęczyszczykiewicz")
    String surname;
}