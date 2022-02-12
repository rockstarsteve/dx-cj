package com.ruoyi.cj.domain;

import com.ruoyi.common.annotation.Excel;

import java.io.Serializable;

/**
 * description
 *
 * @author yaojian
 * @createTime 2022/01/25
 */
public class EmpExcle implements Serializable {
    /** 用户账号 */
    @Excel(name = "员工工号")
    private String empNo;

    /** 用户昵称 */
    @Excel(name = "员工姓名")
    private String name;

    /**
     * 电话号码
     */
    @Excel(name = "电话号码")
    private String phone;

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
