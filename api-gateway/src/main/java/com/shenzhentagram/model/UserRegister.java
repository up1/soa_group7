package com.shenzhentagram.model;

import io.swagger.annotations.ApiModelProperty;

public class UserRegister extends UserBase {

    @ApiModelProperty(required = true, position = 1)
    private String username;

    @ApiModelProperty(required = true, position = 2)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
