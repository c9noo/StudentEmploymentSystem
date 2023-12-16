package com.employment.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryAlumniVo
 * @author: c9noo
 * @create: 2023-12-14 15:14
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QueryAlumniVo implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 当前职位
     */
    private String currentPosition;

    /**
     * 在职公司
     */
    private String currentCompany;

    /**
     * 地点
     */
    private String location;

    /**
     * 薪水
     */
    private BigDecimal salary;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 毕业时间
     */
    private LocalDateTime graduationDate;

    /**
     * 班主任
     */
    private String classAdvisor;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;

    private static final long serialVersionUID = 1L;
}
