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
import com.ruoyi.cj.domain.Prize;
import com.ruoyi.cj.service.IPrizeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 奖品Controller
 * 
 * @author yj
 * @date 2022-01-23
 */
@RestController
@RequestMapping("/cj/prize")
public class PrizeController extends BaseController
{
    @Autowired
    private IPrizeService prizeService;

    /**
     * 查询奖品列表
     */
    @PreAuthorize("@ss.hasPermi('cj:prize:list')")
    @GetMapping("/list")
    public TableDataInfo list(Prize prize)
    {
        startPage();
        List<Prize> list = prizeService.selectPrizeList(prize);
        return getDataTable(list);
    }

    /**
     * 导出奖品列表
     */
    @PreAuthorize("@ss.hasPermi('cj:prize:export')")
    @Log(title = "奖品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Prize prize)
    {
        List<Prize> list = prizeService.selectPrizeList(prize);
        ExcelUtil<Prize> util = new ExcelUtil<Prize>(Prize.class);
        util.exportExcel(response, list, "奖品数据");
    }

    /**
     * 获取奖品详细信息
     */
    @PreAuthorize("@ss.hasPermi('cj:prize:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(prizeService.selectPrizeById(id));
    }

    /**
     * 新增奖品
     */
    @PreAuthorize("@ss.hasPermi('cj:prize:add')")
    @Log(title = "奖品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Prize prize)
    {
        return toAjax(prizeService.insertPrize(prize));
    }

    /**
     * 修改奖品
     */
    @PreAuthorize("@ss.hasPermi('cj:prize:edit')")
    @Log(title = "奖品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Prize prize)
    {
        return toAjax(prizeService.updatePrize(prize));
    }

    /**
     * 删除奖品
     */
    @PreAuthorize("@ss.hasPermi('cj:prize:remove')")
    @Log(title = "奖品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(prizeService.deletePrizeByIds(ids));
    }
}
