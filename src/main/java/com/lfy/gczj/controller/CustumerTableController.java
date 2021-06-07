package com.lfy.gczj.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lfy.gczj.entity.CustumerTable;
import com.lfy.gczj.service.CustumerTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (CustumerTable)表控制层
 *
 * @author makejava
 * @since 2019-12-22 17:59:14
 */
@RestController
@RequestMapping("custumerTable")
public class CustumerTableController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CustumerTableService custumerTableService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param custumerTable 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<CustumerTable> page, CustumerTable custumerTable) {
        return success(this.custumerTableService.page(page, new QueryWrapper<>(custumerTable)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.custumerTableService.getById(id));
    }

    /**
     * 新增数据
     * insert(@RequestBody CustumerTable custumerTable)报错 Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported]
     * @param custumerTable 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    public R insert(@RequestBody CustumerTable custumerTable) {
        return success(this.custumerTableService.save(custumerTable));
    }

    /**
     * 修改数据
     *
     * @param custumerTable 实体对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public R update(@RequestBody CustumerTable custumerTable) {
        return success(this.custumerTableService.updateById(custumerTable));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
//    @DeleteMapping
    @PostMapping("/delete")
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.custumerTableService.removeByIds(idList));
    }
}