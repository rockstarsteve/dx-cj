package com.ruoyi.cj.controller;

import com.ruoyi.cj.domain.Emp;
import com.ruoyi.cj.domain.EmpExcle;
import com.ruoyi.cj.domain.Prize;
import com.ruoyi.cj.service.IEmpService;
import com.ruoyi.cj.service.IPrizeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * description
 * 对外的抽奖类
 *
 * @author yaojian
 * @createTime 2022/01/24
 */
@Api("用户信息管理")
@RestController
@RequestMapping("/openapi/cj")
public class CjController extends BaseController {

    @Autowired
    private IEmpService empService;
    @Autowired
    private IPrizeService prizeService;

    @ApiOperation("员工登录")
    @PostMapping("/login")
    public AjaxResult edit(@RequestBody @Validated Emp emp) {
        emp = empService.login(emp);
        return AjaxResult.success(emp);
    }

    @ApiOperation("获取员工中奖的奖品")
    @PostMapping("/getEmpPrize")
    public AjaxResult getPrize(@RequestBody @Validated Emp emp) {
        Prize prize = prizeService.getEmpPrize(emp);
        return AjaxResult.success(prize);
    }

    @ApiOperation("获取奖品列表")
    @PostMapping("/getPrizeList")
    public AjaxResult getPrizeList(@RequestBody Prize prize) {
        List<Prize> prizes = prizeService.selectAllPrizeList(prize);
        return AjaxResult.success(prizes);
    }

    @ApiOperation("抽奖")
    @PostMapping("/cj")
    public AjaxResult cj(@RequestBody @Validated({Emp.RegCheckId.class}) Emp emp) {
        Prize prize = prizeService.cjByEmpNo(emp.getEmpNo());
        return AjaxResult.success(prize);
    }

}
