package com.ruoyi.cj.service.impl;

import java.util.Collections;
import java.util.List;

import com.ruoyi.cj.domain.Emp;
import com.ruoyi.cj.domain.EmpPrize;
import com.ruoyi.cj.mapper.EmpMapper;
import com.ruoyi.cj.mapper.EmpPrizeMapper;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.ruoyi.cj.mapper.PrizeMapper;
import com.ruoyi.cj.domain.Prize;
import com.ruoyi.cj.service.IPrizeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 奖品Service业务层处理
 *
 * @author yj
 * @date 2022-01-23
 */
@Service
public class PrizeServiceImpl implements IPrizeService {
    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpPrizeMapper empPrizeMapper;

    /**
     * 查询奖品
     *
     * @param id 奖品主键
     * @return 奖品
     */
    @Override
    public Prize selectPrizeById(Integer id) {
        return prizeMapper.selectPrizeById(id);
    }

    /**
     * 查询奖品列表
     *
     * @param prize 奖品
     * @return 奖品
     */
    @Override
    public List<Prize> selectPrizeList(Prize prize) {
        return prizeMapper.selectPrizeList(prize);
    }

    /**
     * 新增奖品
     *
     * @param prize 奖品
     * @return 结果
     */
    @Override
    public int insertPrize(Prize prize) {
        return prizeMapper.insertPrize(prize);
    }

    /**
     * 修改奖品
     *
     * @param prize 奖品
     * @return 结果
     */
    @Override
    public int updatePrize(Prize prize) {
        return prizeMapper.updatePrize(prize);
    }

    /**
     * 批量删除奖品
     *
     * @param ids 需要删除的奖品主键
     * @return 结果
     */
    @Override
    public int deletePrizeByIds(Integer[] ids) {
        return prizeMapper.deletePrizeByIds(ids);
    }

    /**
     * 删除奖品信息
     *
     * @param id 奖品主键
     * @return 结果
     */
    @Override
    public int deletePrizeById(Integer id) {
        return prizeMapper.deletePrizeById(id);
    }

    @Override
    public List<Prize> selectAllPrizeList(Prize queryPrize) {
        List<Prize> prizes = prizeMapper.selectPrizeList(queryPrize);
        Collections.shuffle(prizes);
        for (int i = 0; i < 8; i++) {
            if (prizes.size() < 8) {
                increament(prizes);
            } else {
                break;
            }
        }
        return prizes;
    }

    @Transactional
    @Override
    public Prize cjByEmpNo(String empNo) {

        //1：先查看员工信息和中奖信息
        Emp emp = checkEmp(empNo);
        EmpPrize empPrize = empPrizeMapper.selectEmpPrizeByEmpNo(emp.getId());
        if (empPrize != null) {
            throw new RuntimeException("您已经抽过奖，请检查！");
        }

        //2：抽奖
        Prize randomPrize = null;
        List<Prize> hasPrizeList = prizeMapper.selectHasPrizeList();
        if (hasPrizeList == null || hasPrizeList.size() < 1) {
            //如果没有奖品奖品存在，那么把没中奖的奖品查询出来进行设置到用户上
            Prize queryPrize = new Prize();
            queryPrize.setWin("N");
            List<Prize> prizeList = prizeMapper.selectPrizeList(queryPrize);
            if (prizeList == null || prizeList.size() < 1) {
                throw new RuntimeException("奖品已经抽完，感谢您的参与~");
            }
            Collections.shuffle(prizeList);
            randomPrize = prizeList.get(0);
        } else {
            //如果用户有权重，那么就给用户中奖的奖品
            if (emp.getWeighting() > 0) {
                for (Prize prize : hasPrizeList) {
                    if (prize.getWin().equals("Y")) {
                        randomPrize = prize;
                        break;
                    }
                }
                //存在只剩下没有中奖的奖品，那么还是给那个
                if (randomPrize == null) {
                    randomPrize = hasPrizeList.get(0);
                }
            } else {
                Collections.shuffle(hasPrizeList);
                randomPrize = hasPrizeList.get(0);
            }
        }

        //3：把奖品数据减少
        randomPrize.setStock(randomPrize.getStock() - 1);
        prizeMapper.updatePrize(randomPrize);


        //4:然后插入中间表
        EmpPrize insertEmpPrize = new EmpPrize();
        insertEmpPrize.setEmpId(emp.getId());
        insertEmpPrize.setPrizeId(randomPrize.getId());
        empPrizeMapper.insertEmpPrize(insertEmpPrize);

        return randomPrize;
    }

    /**
     * 检查员工信息
     *
     * @param empNo
     * @return
     */
    private Emp checkEmp(String empNo) {
        //根据工号查看员工
        Emp emp = empMapper.selectEmpByEmpNo(empNo);
        if (emp == null || emp.getId() == null) {
            throw new RuntimeException("你的信息存在问题，请检查");
        } else {
            return emp;
        }
    }

    @Override
    public Prize getEmpPrize(Emp emp) {
        Emp queryEmp = empMapper.selectEmpByEmpNo(emp.getEmpNo());
        if (queryEmp == null || StringUtils.isEmpty(queryEmp.getEmpNo())) {
            throw new RuntimeException("登录信息有误，请重新登录!");
        }
        List<Prize> prizeList = prizeMapper.selectPrizeByEmpNo(emp.getEmpNo());
        if (prizeList == null || prizeList.size() == 0) {
            return null;
        }
        if (prizeList.size() > 1) {
            throw new RuntimeException("数据有误，请联系管理员!");
//            return prizeList.get(0);
        } else {
            return prizeList.get(0);
        }
    }

    /**
     * 加1后随机
     *
     * @param prizeList
     * @return
     */
    private List<Prize> increament(List<Prize> prizeList) {
        Prize prize = prizeList.get(0);
        prizeList.add(prize);
        Collections.shuffle(prizeList);
        return prizeList;
    }
}
