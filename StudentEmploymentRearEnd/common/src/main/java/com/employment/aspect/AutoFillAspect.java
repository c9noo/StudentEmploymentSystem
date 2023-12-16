package com.employment.aspect;

import com.employment.annotation.AutoFill;
import com.employment.constant.AutoFillConstant;
import com.employment.enums.OperationTypeEnum;
import com.employment.utils.SecurityUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AutoFillAspect
 * @author: c9noo
 * @create: 2023-11-13 10:34
 * @Version 1.0
 **/
@Component
@Aspect
public class AutoFillAspect {

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.employment.mapper.*.*(..)) && @annotation(com.employment.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){

        //获取到被拦截的方法上面的数据库操作类型
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();  //方法签名对象
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);  //获得方法上的注解对象
        OperationTypeEnum value = autoFill.value(); //获得数据库操作类型
        //获取到当前被拦截方法的参数 -- 实体对象  因为我们是要往实体对象里面赋值
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0){
            return;
        }
        Object entity = args[0];

        //获取数据，为当前不同的操作进行对应的赋值
        LocalDateTime now = LocalDateTime.now();
        Long userId = SecurityUtil.getUserId();

        /**
         * 如果是插入，对四个字段进行赋值
         */
        if (value == OperationTypeEnum.INSERT){
            try {
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class).invoke(entity,now);
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class).invoke(entity,userId);
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class).invoke(entity,now);
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class).invoke(entity,userId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        /**
         * 如果是更新，对更新人和时间进行赋值
         */
        if (value == OperationTypeEnum.UPDATE){
            try {
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class).invoke(entity,now);
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class).invoke(entity,userId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }
}
