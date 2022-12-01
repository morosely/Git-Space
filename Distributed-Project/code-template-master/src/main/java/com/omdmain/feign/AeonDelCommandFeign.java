package com.changgou.content.feign;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="omdmain")
@RequestMapping("/aeonDelCommand")
public interface AeonDelCommandFeign {

    /***
     * AeonDelCommand分页条件搜索实现
     * @param aeonDelCommand
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) AeonDelCommand aeonDelCommand, @PathVariable  int page, @PathVariable  int size);

    /***
     * AeonDelCommand分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param aeonDelCommand
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<AeonDelCommand>> findList(@RequestBody(required = false) AeonDelCommand aeonDelCommand);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable  id);

    /***
     * 修改AeonDelCommand数据
     * @param aeonDelCommand
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody AeonDelCommand aeonDelCommand,@PathVariable  id);

    /***
     * 新增AeonDelCommand数据
     * @param aeonDelCommand
     * @return
     */
    @PostMapping
    Result add(@RequestBody AeonDelCommand aeonDelCommand);

    /***
     * 根据ID查询AeonDelCommand数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<AeonDelCommand> findById(@PathVariable  id);

    /***
     * 查询AeonDelCommand全部数据
     * @return
     */
    @GetMapping
    Result<List<AeonDelCommand>> findAll();
}