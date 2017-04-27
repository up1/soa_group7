package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"profile_picture"})
public class UserUpdate extends UserBase {}
