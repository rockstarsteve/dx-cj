package com.ruoyi.cj.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.cj.domain.EmpExcle;
import com.ruoyi.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.cj.domain.Emp;
import com.ruoyi.cj.service.IEmpService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 员工Controller
 * 
 * @author yj
 * @date 2022-01-23
 */
@RestController
@RequestMapping("/cj/emp")
public class EmpController extends BaseController
{
    @Autowired
    private IEmpService empService;

    /**
     * 查询员工列表
     */
    @PreAuthorize("@ss.hasPermi('cj:emp:list')")
    @GetMapping("/list")
    public TableDataInfo list(Emp emp)
    {
        startPage();
        List<Emp> list = empService.selectEmpList(emp);
        return getDataTable(list);
    }

    /**
     * 导出员工列表
     */
    @PreAuthorize("@ss.hasPermi('cj:emp:export')")
    @Log(title = "员工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Emp emp)
    {
        List<Emp> list = empService.selectEmpList(emp);
        ExcelUtil<Emp> util = new ExcelUtil<Emp>(Emp.class);
        util.exportExcel(response, list, "员工数据");
    }

    /**
     * 获取员工详细信息
     */
    @PreAuthorize("@ss.hasPermi('cj:emp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(empService.selectEmpById(id));
    }

    /**
     * 新增员工
     */
    @PreAuthorize("@ss.hasPermi('cj:emp:add')")
    @Log(title = "员工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Emp emp)
    {
        return toAjax(empService.insertEmp(emp));
    }

    /**
     * 修改员工
     */
    @PreAuthorize("@ss.hasPermi('cj:emp:edit')")
    @Log(title = "员工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Emp emp)
    {
        return toAjax(empService.updateEmp(emp));
    }

    /**
     * 删除员工
     */
    @PreAuthorize("@ss.hasPermi('cj:emp:remove')")
    @Log(title = "员工", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(empService.deleteEmpByIds(ids));
    }

    @ApiOperation("获取抽奖的模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<EmpExcle> util = new ExcelUtil<EmpExcle>(EmpExcle.class);
        util.importTemplateExcel(response, "用户数据");
    }

    @Log(title = "员工导入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('cj:emp:add')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<EmpExcle> util = new ExcelUtil<EmpExcle>(EmpExcle.class);
        List<EmpExcle> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = empService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }
}
