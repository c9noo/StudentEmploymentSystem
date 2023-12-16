package com.employment.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName UserContextDto
 * @author: c9noo
 * @create: 2023-12-06 08:46
 * @Version 1.0
 * 封装用户的id和它的角色Key
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserContextDto {
    private Long userId;
    private List<String> roleKeyByUserId;
}
