package com.ruoyi.cj.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * 员工对象 cj_emp
 *
 * @author yj
 * @date 2022-01-23
 */
public class Emp extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 只校验考试id是否为空
     */
    public interface RegCheckId {
    }

    /**
     * id
     */
    private Integer id;

    /**
     * 员工编号
     */
    @NotBlank(message = "员工工号不能为空", groups = {RegCheckId.class})
    @Length(min = 1, max = 10, message = "工号长度不能超过10个字符", groups = {RegCheckId.class})
    @Excel(name = "员工编号")
    private String empNo;

    /**
     * 员工姓名
     */
    @NotBlank(message = "员工姓名不能为空")
    @Length(min = 1, max = 10, message = "姓名长度不能操过10个")
    @Excel(name = "员工姓名")
    private String name;

    /**
     * 电话号码
     */
    @Excel(name = "电话号码")
    private String phone;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Integer weighting;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setWeighting(Integer weighting) {
        this.weighting = weighting;
    }

    public Integer getWeighting() {
        return weighting;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("empNo", getEmpNo())
                .append("name", getName())
                .append("phone", getPhone())
                .append("weighting", getWeighting())
                .toString();
    }
}
