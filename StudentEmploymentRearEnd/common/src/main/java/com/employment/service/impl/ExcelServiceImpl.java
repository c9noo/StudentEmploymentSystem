package com.employment.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.employment.pojo.dto.ImportAlumniDto;
import com.employment.pojo.dto.ImportUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ExcelServiceImpl
 * @author: c9noo
 * @create: 2023-11-27 09:22
 * @Version 1.0
 **/
@Service
@Slf4j
public class ExcelServiceImpl {

    private String filePath;

    public List<ImportUserDto> readExcel(String filePath) {

        this.filePath = filePath;
        //创建一个空集合
        List<ImportUserDto> userList = new ArrayList<>();
        /**
         * 1. 文件的路径
         * 2. 要解析之后转换成为的类型
         * 3. 创建了一个 UserDataListener 对象，这个对象将用于监听 Excel 数据的解析事件。当 Excel 数据被解析时，会调用这个监听器的相应方法
         */
        EasyExcel.read(filePath, ImportUserDto.class, new UserDataListener(userList)).excelType(ExcelTypeEnum.XLSX).sheet().doRead();
        return userList;
    }

    public class UserDataListener extends AnalysisEventListener<ImportUserDto> {

        // 用于存储解析后的 ImportUserDto 对象的列表
        private List<ImportUserDto> userList;

        // 构造函数，用于初始化监听器并传入 userList
        public UserDataListener(List<ImportUserDto> userList) {
            this.userList = userList;
        }

        // 在 Excel 表中的每一行数据解析时调用的方法
        @Override
        public void invoke(ImportUserDto data, AnalysisContext context) {
            userList.add(data);
        }

        // 在所有数据都被解析后调用的方法
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            /**
             * 解析完成后的操作，可在此处插入数据库等,这边进行删除
             */
            try {
                // 创建 File 对象
                File file = new File(filePath);

                // 检查文件是否存在
                if (file.exists()) {
                    // 删除文件
                    if (file.delete()) {
                        log.info("文件删除成功");
                    } else {
                        log.info("文件删除失败");
                    }
                } else {
                    log.info("文件不存在");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public List<ImportAlumniDto> readExcelAlumni(String filePath) {

        this.filePath = filePath;
        //创建一个空集合
        List<ImportAlumniDto> userList = new ArrayList<>();
        /**
         * 1. 文件的路径
         * 2. 要解析之后转换成为的类型
         * 3. 创建了一个 AlumniDataListener 对象，这个对象将用于监听 Excel 数据的解析事件。当 Excel 数据被解析时，会调用这个监听器的相应方法
         */
        EasyExcel.read(filePath, ImportAlumniDto.class, new AlumniDataListener(userList)).excelType(ExcelTypeEnum.XLSX).sheet().doRead();
        return userList;
    }

    public class AlumniDataListener extends AnalysisEventListener<ImportAlumniDto>{

        // 用于存储解析后的 ImportUserDto 对象的列表
        private List<ImportAlumniDto> userList;

        // 构造函数，用于初始化监听器并传入 userList
        public AlumniDataListener(List<ImportAlumniDto> userList) {
            this.userList = userList;
        }


        @Override
        public void invoke(ImportAlumniDto importAlumniDto, AnalysisContext analysisContext) {
            userList.add(importAlumniDto);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            /**
             * 解析完成后的操作，可在此处插入数据库等,这边进行删除
             */
            try {
                // 创建 File 对象
                File file = new File(filePath);

                // 检查文件是否存在
                if (file.exists()) {
                    // 删除文件
                    if (file.delete()) {
                        log.info("文件删除成功");
                    } else {
                        log.info("文件删除失败");
                    }
                } else {
                    log.info("文件不存在");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
