package com.ruoyi.cj.service;

import java.util.List;

import com.ruoyi.cj.domain.Emp;
import com.ruoyi.cj.domain.Prize;

/**
 * 奖品Service接口
 * 
 * @author yj
 * @date 2022-01-23
 */
public interface IPrizeService 
{
    /**
     * 查询奖品
     * 
     * @param id 奖品主键
     * @return 奖品
     */
    public Prize selectPrizeById(Integer id);

    /**
     * 查询奖品列表
     * 
     * @param prize 奖品
     * @return 奖品集合
     */
    public List<Prize> selectPrizeList(Prize prize);

    /**
     * 新增奖品
     * 
     * @param prize 奖品
     * @return 结果
     */
    public int insertPrize(Prize prize);

    /**
     * 修改奖品
     * 
     * @param prize 奖品
     * @return 结果
     */
    public int updatePrize(Prize prize);

    /**
     * 批量删除奖品
     * 
     * @param ids 需要删除的奖品主键集合
     * @return 结果
     */
    public int deletePrizeByIds(Integer[] ids);

    /**
     * 删除奖品信息
     * 
     * @param id 奖品主键
     * @return 结果
     */
    public int deletePrizeById(Integer id);

    /**
     * 获取所有的奖品
     * @param queryPrize
     * @return
     */
    List<Prize> selectAllPrizeList(Prize queryPrize);

    /**
     * 根据员工工号抽奖
     * @param empNo
     * @return
     */
    Prize cjByEmpNo(String empNo);

    /**
     * 获取员工中奖信息
     * @param emp
     * @return
     */
    Prize getEmpPrize(Emp emp);
}
