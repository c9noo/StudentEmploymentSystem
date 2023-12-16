package com.employment.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @program: StudentEmploymentSystem
 * @ClassName LocalDateStringConverter
 * @author: c9noo
 * @create: 2023-12-15 12:28
 * @Version 1.0
 * 将 LocalDate 类型中的数据转化为 yyyy-MM-dd 的格式(导入|导出)到Excel中
 **/
public class LocalDateStringConverter implements Converter<LocalDate> {
    @Override
    public Class supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration)  {
        return LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern("yyyy/M/d"));
    }
    @Override
    public WriteCellData<?> convertToExcelData(LocalDate localDate, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        WriteCellData cellData = new WriteCellData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String cellValue;
        cellValue=formatter.format(localDate);
        cellData.setType(CellDataTypeEnum.STRING);
        cellData.setStringValue(cellValue);
        cellData.setData(cellValue);
        return cellData;
    }
}
