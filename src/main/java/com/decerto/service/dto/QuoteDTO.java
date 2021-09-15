package com.decerto.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("quote")
public class QuoteDTO {

    @ApiModelProperty(value = "Author name", position = 1, example = "Grzegorz")
    String name;

    @ApiModelProperty(value = "Author surname", position = 2, example = "Brzęczyszczykiewicz")
    String surname;

    @ApiModelProperty(value = "Quote content", position = 3, example = "String")
    String content;
}