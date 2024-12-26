package com.cershy.linyuminiserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;
    private String name;
    private String avatar;
    private List<String> badge;
    private String ipOwnership;
}
