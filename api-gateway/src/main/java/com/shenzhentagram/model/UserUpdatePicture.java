package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"full_name", "bio", "display_name"})
public class UserUpdatePicture extends UserBase {}
