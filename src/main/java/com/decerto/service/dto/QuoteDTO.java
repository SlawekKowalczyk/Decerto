package com.decerto.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("quote")
public class QuoteDTO {

    @ApiModelProperty(value = "Quote Author", position = 1)
    AuthorDto author;

    @ApiModelProperty(value = "Quote content", position = 2)
    String content;
}