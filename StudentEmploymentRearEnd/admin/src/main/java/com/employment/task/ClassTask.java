package com.employment.task;

import com.employment.constant.ClassGraduateStatusConstant;
import com.employment.mapper.ClassInfoMapper;
import com.employment.pojo.dto.ClassIdAndTime;
import com.employment.pojo.entity.ClassInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ClassTask
 * @author: c9noo
 * @create: 2023-12-29 16:16
 * @Version 1.0
 **/
@Component
public class ClassTask {

    @Autowired
    private ClassInfoMapper classInfoMapper;

    /**
     * 获取当前时间
     */
    private LocalDate currentDate = LocalDate.now();

    /**
     * 检查是否进入毕业班时间
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void checkClassGraduateStatus(){

        // 先获取到 未进入毕业班的信息
        List<ClassIdAndTime> classIdAndTimeList =  classInfoMapper.selectIdAndTime(ClassGraduateStatusConstant.unGraduated);

        // 如果毕业时间 距离现在的时间已经小于等于6个月，进入毕业班状态
        classIdAndTimeList.stream()
                .filter(classIdAndTime -> ChronoUnit.MONTHS.between(currentDate,classIdAndTime.getGraduationDate()) <= ClassGraduateStatusConstant.graduatedTime)
                .forEach(classIdAndTime -> {
                    ClassInformation classInformation = new ClassInformation();
                    classInformation.setStatus(ClassGraduateStatusConstant.graduatedClass);
                    classInformation.setId(classIdAndTime.getId());
                    classInfoMapper.updateById(classInformation);
                });
    }

    /**
     * 检查是否超出毕业时间
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void checkGraduateStatus(){

        // 先获取到 已进入毕业班的信息
        List<ClassIdAndTime> classIdAndTimeList =  classInfoMapper.selectIdAndTime(ClassGraduateStatusConstant.graduatedClass);

        // 如果毕业时间已经大于等于当前的时间 修改状态
        classIdAndTimeList.stream()
                .filter(classIdAndTime -> classIdAndTime.getGraduationDate().isBefore(currentDate))
                .forEach(classIdAndTime -> {
                    ClassInformation classInformation = new ClassInformation();
                    classInformation.setStatus(ClassGraduateStatusConstant.graduated);
                    classInformation.setId(classIdAndTime.getId());
                    classInfoMapper.updateById(classInformation);
                });

    }

}
