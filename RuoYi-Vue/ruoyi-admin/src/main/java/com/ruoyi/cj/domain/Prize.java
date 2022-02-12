package com.ruoyi.cj.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 奖品对象 cj_prize
 * 
 * @author yj
 * @date 2022-01-23
 */
public class Prize extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 奖品名称 */
    @Excel(name = "奖品名称")
    private String name;

    /** 奖品信息 */
    @Excel(name = "奖品信息")
    private String info;

    /** 背景图片 */
    @Excel(name = "背景图片")
    private String backImg;

    /** 奖品数量 */
    @Excel(name = "奖品数量")
    private Integer stock;

    /** 背景图片 */
    @Excel(name = "是否中奖")
    private String win;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }
    public void setBackImg(String backImg) 
    {
        this.backImg = backImg;
    }

    public String getBackImg() 
    {
        return backImg;
    }
    public void setStock(Integer stock) 
    {
        this.stock = stock;
    }

    public Integer getStock() 
    {
        return stock;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("info", getInfo())
            .append("backImg", getBackImg())
            .append("stock", getStock())
            .append("remark", getRemark())
            .toString();
    }
}
