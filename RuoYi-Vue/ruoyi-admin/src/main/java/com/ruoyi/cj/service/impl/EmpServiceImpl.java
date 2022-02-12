package com.ruoyi.cj.service.impl;

import java.util.List;

import com.ruoyi.cj.domain.EmpExcle;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cj.mapper.EmpMapper;
import com.ruoyi.cj.domain.Emp;
import com.ruoyi.cj.service.IEmpService;

/**
 * 员工Service业务层处理
 *
 * @author yj
 * @date 2022-01-23
 */
@Service
public class EmpServiceImpl implements IEmpService {

    private static final Logger log = LoggerFactory.getLogger(EmpServiceImpl.class);

    @Autowired
    private EmpMapper empMapper;

    /**
     * 查询员工
     *
     * @param id 员工主键
     * @return 员工
     */
    @Override
    public Emp selectEmpById(Integer id) {
        return empMapper.selectEmpById(id);
    }

    /**
     * 查询员工列表
     *
     * @param emp 员工
     * @return 员工
     */
    @Override
    public List<Emp> selectEmpList(Emp emp) {
        return empMapper.selectEmpList(emp);
    }

    /**
     * 新增员工
     *
     * @param emp 员工
     * @return 结果
     */
    @Override
    public int insertEmp(Emp emp) {
        return empMapper.insertEmp(emp);
    }

    /**
     * 修改员工
     *
     * @param emp 员工
     * @return 结果
     */
    @Override
    public int updateEmp(Emp emp) {
        return empMapper.updateEmp(emp);
    }

    /**
     * 批量删除员工
     *
     * @param ids 需要删除的员工主键
     * @return 结果
     */
    @Override
    public int deleteEmpByIds(Integer[] ids) {
        return empMapper.deleteEmpByIds(ids);
    }

    /**
     * 删除员工信息
     *
     * @param id 员工主键
     * @return 结果
     */
    @Override
    public int deleteEmpById(Integer id) {
        return empMapper.deleteEmpById(id);
    }

    @Override
    public Emp login(Emp emp) {
        Emp queryEmp = new Emp();
        queryEmp.setEmpNo(emp.getEmpNo());
        queryEmp.setName(emp.getName());
        List<Emp> emps = empMapper.selectEmpList(queryEmp);
        if (emps.size() == 1) {
            return emps.get(0);
        } else if (emps.size() > 1) {
            throw new RuntimeException("员工信息存在问题");
        } else {
            throw new RuntimeException("员工信息不存在");
        }
    }

    @Override
    public String importUser(List<EmpExcle> empList, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(empList) || empList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (EmpExcle emp : empList) {
            try {
                // 验证是否存在这个用户
                Emp selectEmp = empMapper.selectEmpByEmpNo(emp.getEmpNo());
                if (StringUtils.isNull(selectEmp)) {
                    Emp insertEmp = new Emp();
                    insertEmp.setEmpNo(emp.getEmpNo());
                    insertEmp.setPhone(emp.getPhone());
                    insertEmp.setName(emp.getName());
                    insertEmp.setWeighting(0);
                    empMapper.insertEmp(insertEmp);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、员工 " + insertEmp.getName() + " 导入成功");
                } else if (isUpdateSupport) {
                    Emp insertEmp = new Emp();
                    insertEmp.setEmpNo(emp.getEmpNo());
                    insertEmp.setPhone(emp.getPhone());
                    insertEmp.setName(emp.getName());
                    insertEmp.setWeighting(0);
                    empMapper.updateEmpByEmpNo(insertEmp);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、员工 " + emp.getName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、员工 " + emp.getName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、员工 " + emp.getName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
