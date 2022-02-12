package com.ruoyi.cj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cj.mapper.EmpPrizeMapper;
import com.ruoyi.cj.domain.EmpPrize;
import com.ruoyi.cj.service.IEmpPrizeService;

/**
 * 员工中奖中间Service业务层处理
 *
 * @author yj
 * @date 2022-01-23
 */
@Service
public class EmpPrizeServiceImpl implements IEmpPrizeService {
    @Autowired
    private EmpPrizeMapper empPrizeMapper;

    /**
     * 查询员工中奖中间
     *
     * @param id 员工中奖中间主键
     * @return 员工中奖中间
     */
    @Override
    public EmpPrize selectEmpPrizeById(Integer id) {
        return empPrizeMapper.selectEmpPrizeById(id);
    }

    /**
     * 查询员工中奖中间列表
     *
     * @param empPrize 员工中奖中间
     * @return 员工中奖中间
     */
    @Override
    public List<EmpPrize> selectEmpPrizeList(EmpPrize empPrize) {
        return empPrizeMapper.selectEmpPrizeList(empPrize);
    }

    /**
     * 新增员工中奖中间
     *
     * @param empPrize 员工中奖中间
     * @return 结果
     */
    @Override
    public int insertEmpPrize(EmpPrize empPrize) {
        EmpPrize queryEmpPrize = new EmpPrize();
        queryEmpPrize.setEmpId(empPrize.getEmpId());
        List<EmpPrize> empPrizes = empPrizeMapper.selectEmpPrizeList(queryEmpPrize);
        if (empPrize != null && empPrizes.size() > 0) {
            throw new RuntimeException("该员工已经中奖，不能再插入中奖信息！");
        }
        return empPrizeMapper.insertEmpPrize(empPrize);
    }

    /**
     * 修改员工中奖中间
     *
     * @param empPrize 员工中奖中间
     * @return 结果
     */
    @Override
    public int updateEmpPrize(EmpPrize empPrize) {
        return empPrizeMapper.updateEmpPrize(empPrize);
    }

    /**
     * 批量删除员工中奖中间
     *
     * @param ids 需要删除的员工中奖中间主键
     * @return 结果
     */
    @Override
    public int deleteEmpPrizeByIds(Integer[] ids) {
        return empPrizeMapper.deleteEmpPrizeByIds(ids);
    }

    /**
     * 删除员工中奖中间信息
     *
     * @param id 员工中奖中间主键
     * @return 结果
     */
    @Override
    public int deleteEmpPrizeById(Integer id) {
        return empPrizeMapper.deleteEmpPrizeById(id);
    }
}
