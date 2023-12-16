package com.employment.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName MenusVo
 * @author: c9noo
 * @create: 2023-11-10 09:10
 * @Version 1.0
 * getMenus 接口的返回类型 包含了menus
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenusVo {
    private List<MenuVo> menus;
}
