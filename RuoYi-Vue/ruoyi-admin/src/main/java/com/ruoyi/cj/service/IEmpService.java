package com.ruoyi.cj.service;

import java.util.List;
import com.ruoyi.cj.domain.Emp;
import com.ruoyi.cj.domain.EmpExcle;

/**
 * 员工Service接口
 * 
 * @author yj
 * @date 2022-01-23
 */
public interface IEmpService 
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
     * 批量删除员工
     * 
     * @param ids 需要删除的员工主键集合
     * @return 结果
     */
    public int deleteEmpByIds(Integer[] ids);

    /**
     * 删除员工信息
     * 
     * @param id 员工主键
     * @return 结果
     */
    public int deleteEmpById(Integer id);

    /**
     * 员工登录查询
     * @param emp
     * @return
     */
    Emp login(Emp emp);

    /**
     * 导入员工信息
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    String importUser(List<EmpExcle> userList, boolean isUpdateSupport, String operName);
}
