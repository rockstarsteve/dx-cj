package com.ruoyi.cj.mapper;

import java.util.List;
import com.ruoyi.cj.domain.Prize;

/**
 * 奖品Mapper接口
 * 
 * @author yj
 * @date 2022-01-23
 */
public interface PrizeMapper 
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
     * 删除奖品
     * 
     * @param id 奖品主键
     * @return 结果
     */
    public int deletePrizeById(Integer id);

    /**
     * 批量删除奖品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePrizeByIds(Integer[] ids);

    /**
     * 获取员工的奖品信息
     * @param empNo
     * @return
     */
    List<Prize> selectPrizeByEmpNo(String empNo);

    /**
     * 查询还有奖品的内容
     * @return
     */
    List<Prize> selectHasPrizeList();

}
