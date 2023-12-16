package com.employment.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: StudentEmploymentSystem
 * @ClassName BeanCopyUtil
 * @author: c9noo
 * @create: 2023-11-09 09:08
 * @Version 1.0
 * Bean拷贝工具类
 **/
public class BeanCopyUtil {
    private BeanCopyUtil() {
    }

    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}