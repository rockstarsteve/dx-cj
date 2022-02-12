package com.ruoyi.cj.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.cj.domain.EmpPrize;
import com.ruoyi.cj.service.IEmpPrizeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工中奖中间Controller
 * 
 * @author yj
 * @date 2022-01-23
 */
@RestController
@RequestMapping("/cj/empprize")
public class EmpPrizeController extends BaseController
{
    @Autowired
    private IEmpPrizeService empPrizeService;

    /**
     * 查询员工中奖中间列表
     */
    @PreAuthorize("@ss.hasPermi('cj:empprize:list')")
    @GetMapping("/list")
    public TableDataInfo list(EmpPrize empPrize)
    {
        startPage();
        List<EmpPrize> list = empPrizeService.selectEmpPrizeList(empPrize);
        return getDataTable(list);
    }

    /**
     * 导出员工中奖中间列表
     */
    @PreAuthorize("@ss.hasPermi('cj:empprize:export')")
    @Log(title = "员工中奖中间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmpPrize empPrize)
    {
        List<EmpPrize> list = empPrizeService.selectEmpPrizeList(empPrize);
        ExcelUtil<EmpPrize> util = new ExcelUtil<EmpPrize>(EmpPrize.class);
        util.exportExcel(response, list, "员工中奖中间数据");
    }

    /**
     * 获取员工中奖中间详细信息
     */
    @PreAuthorize("@ss.hasPermi('cj:empprize:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(empPrizeService.selectEmpPrizeById(id));
    }

    /**
     * 新增员工中奖中间
     */
    @PreAuthorize("@ss.hasPermi('cj:empprize:add')")
    @Log(title = "员工中奖中间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmpPrize empPrize)
    {
        return toAjax(empPrizeService.insertEmpPrize(empPrize));
    }

    /**
     * 修改员工中奖中间
     */
    @PreAuthorize("@ss.hasPermi('cj:empprize:edit')")
    @Log(title = "员工中奖中间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmpPrize empPrize)
    {
        return toAjax(empPrizeService.updateEmpPrize(empPrize));
    }

    /**
     * 删除员工中奖中间
     */
    @PreAuthorize("@ss.hasPermi('cj:empprize:remove')")
    @Log(title = "员工中奖中间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(empPrizeService.deleteEmpPrizeByIds(ids));
    }
}
