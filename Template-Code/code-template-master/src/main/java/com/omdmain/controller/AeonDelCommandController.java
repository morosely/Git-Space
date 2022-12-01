package com.omdmain.controller;
import com.omdmain.pojo.AeonDelCommand;
import com.omdmain.service.AeonDelCommandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/aeonDelCommand")
@CrossOrigin
public class AeonDelCommandController {

    @Autowired
    private AeonDelCommandService aeonDelCommandService;

    /***
     * AeonDelCommand分页条件搜索实现
     * @param aeonDelCommand
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  AeonDelCommand aeonDelCommand, @PathVariable  int page, @PathVariable  int size){
        //调用AeonDelCommandService实现分页条件查询AeonDelCommand
        PageInfo<AeonDelCommand> pageInfo = aeonDelCommandService.findPage(aeonDelCommand, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * AeonDelCommand分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用AeonDelCommandService实现分页查询AeonDelCommand
        PageInfo<AeonDelCommand> pageInfo = aeonDelCommandService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param aeonDelCommand
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<AeonDelCommand>> findList(@RequestBody(required = false)  AeonDelCommand aeonDelCommand){
        //调用AeonDelCommandService实现条件查询AeonDelCommand
        List<AeonDelCommand> list = aeonDelCommandService.findList(aeonDelCommand);
        return new Result<List<AeonDelCommand>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable  id){
        //调用AeonDelCommandService实现根据主键删除
        aeonDelCommandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改AeonDelCommand数据
     * @param aeonDelCommand
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  AeonDelCommand aeonDelCommand,@PathVariable  id){
        //设置主键值
        aeonDelCommand.FreeMarker template error (DEBUG mode; use RETHROW in production!):
The following has evaluated to null or missing:
==> keySetMethod  [in template "Controller.java" at line 111, column 20]

----
Tip: If the failing expression is known to be legally refer to something that's sometimes null or missing, either specify a default value like myOptionalVar!myDefault, or use <#if myOptionalVar??>when-present<#else>when-missing</#if>. (These only cover the last step of the expression; to cover the whole expression, use parenthesis: (myOptionalVar.foo)!myDefault, (myOptionalVar.foo)??
----

----
FTL stack trace ("~" means nesting-related):
	- Failed at: ${keySetMethod}  [in template "Controller.java" at line 111, column 18]
----

Java stack trace (for programmers):
----
freemarker.core.InvalidReferenceException: [... Exception message was already printed; see it above ...]
	at freemarker.core.InvalidReferenceException.getInstance(InvalidReferenceException.java:131)
	at freemarker.core.EvalUtil.coerceModelToString(EvalUtil.java:355)
	at freemarker.core.Expression.evalAndCoerceToString(Expression.java:82)
	at freemarker.core.DollarVariable.accept(DollarVariable.java:41)
	at freemarker.core.Environment.visit(Environment.java:324)
	at freemarker.core.MixedContent.accept(MixedContent.java:54)
	at freemarker.core.Environment.visit(Environment.java:324)
	at freemarker.core.Environment.process(Environment.java:302)
	at freemarker.template.Template.process(Template.java:325)
	at com.itheima.code.build.TemplateUtil.writer(TemplateUtil.java:53)
	at com.itheima.code.build.BuilderFactory.builder(BuilderFactory.java:37)
	at com.itheima.code.build.ControllerBuilder.builder(ControllerBuilder.java:23)
	at com.itheima.code.build.TemplateBuilder.builder(TemplateBuilder.java:190)
	at com.itheima.CodeApplication.main(CodeApplication.java:14)
