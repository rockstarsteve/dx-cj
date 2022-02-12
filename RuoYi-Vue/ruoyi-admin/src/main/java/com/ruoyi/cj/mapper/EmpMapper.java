package com.ruoyi.cj.mapper;

import java.util.List;
import com.ruoyi.cj.domain.Emp;

/**
 * 员工Mapper接口
 * 
 * @author yj
 * @date 2022-01-23
 */
public interface EmpMapper 
{
    /**
     * 查询员工
     * 
     * @param id 员工主键
     * @return 员工
     */
    public Emp selectEmpById(Integer id);

    /**
     * 查询员工列表
     * 
     * @param emp 员工
     * @return 员工集合
     */
    public List<Emp> selectEmpList(Emp emp);

    /**
     * 新增员工
     * 
     * @param emp 员工
     * @return 结果
     */
    public int insertEmp(Emp emp);

    /**
     * 修改员工
     * 
     * @param emp 员工
     * @return 结果
     */
    public int updateEmp(Emp emp);

    /**
     * 删除员工
     * 
     * @param id 员工主键
     * @return 结果
     */
    public int deleteEmpById(Integer id);

    /**
     * 批量删除员工
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmpByIds(Integer[] ids);

    /**
     * 根据工号查看员工信息
     * @param empNo
     * @return
     */
    Emp selectEmpByEmpNo(String empNo);

    /**
     * 根据工号更新
     * @param insertEmp
     */
    int updateEmpByEmpNo(Emp insertEmp);

}
