package com.ruoyi.cj.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 员工中奖中间对象 cj_emp_prize
 * 
 * @author yj
 * @date 2022-01-23
 */
public class EmpPrize extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 员工id */
    @Excel(name = "员工id")
    private Integer empId;

    /** 奖品id */
    @Excel(name = "奖品id")
    private Integer prizeId;
    /** 奖品员工工号 */
    private String empNo;
    /** 奖品员工名 */
    private String empName;
    /** 奖品名 */
    private String prizeName;
    /** 奖品信息 */
    private String prizeInfo;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setEmpId(Integer empId) 
    {
        this.empId = empId;
    }

    public Integer getEmpId() 
    {
        return empId;
    }
    public void setPrizeId(Integer prizeId) 
    {
        this.prizeId = prizeId;
    }

    public Integer getPrizeId() 
    {
        return prizeId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeInfo() {
        return prizeInfo;
    }

    public void setPrizeInfo(String prizeInfo) {
        this.prizeInfo = prizeInfo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("empId", getEmpId())
            .append("prizeId", getPrizeId())
            .toString();
    }
}
