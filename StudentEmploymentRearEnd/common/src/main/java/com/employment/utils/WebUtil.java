package com.employment.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: StudentEmploymentSystem
 * @ClassName WebUtil
 * @author: c9noo
 * @create: 2023-11-08 20:26
 * @Version 1.0
 * 处理Web相关的操作的工具类
 **/
public class WebUtil {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static void renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
