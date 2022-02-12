package com.ruoyi.cj.service;

import java.util.List;
import com.ruoyi.cj.domain.EmpPrize;

/**
 * 员工中奖中间Service接口
 * 
 * @author yj
 * @date 2022-01-23
 */
public interface IEmpPrizeService 
{
    /**
     * 查询员工中奖中间
     * 
     * @param id 员工中奖中间主键
     * @return 员工中奖中间
     */
    public EmpPrize selectEmpPrizeById(Integer id);

    /**
     * 查询员工中奖中间列表
     * 
     * @param empPrize 员工中奖中间
     * @return 员工中奖中间集合
     */
    public List<EmpPrize> selectEmpPrizeList(EmpPrize empPrize);

    /**
     * 新增员工中奖中间
     * 
     * @param empPrize 员工中奖中间
     * @return 结果
     */
    public int insertEmpPrize(EmpPrize empPrize);

    /**
     * 修改员工中奖中间
     * 
     * @param empPrize 员工中奖中间
     * @return 结果
     */
    public int updateEmpPrize(EmpPrize empPrize);

    /**
     * 批量删除员工中奖中间
     * 
     * @param ids 需要删除的员工中奖中间主键集合
     * @return 结果
     */
    public int deleteEmpPrizeByIds(Integer[] ids);

    /**
     * 删除员工中奖中间信息
     * 
     * @param id 员工中奖中间主键
     * @return 结果
     */
    public int deleteEmpPrizeById(Integer id);
}
