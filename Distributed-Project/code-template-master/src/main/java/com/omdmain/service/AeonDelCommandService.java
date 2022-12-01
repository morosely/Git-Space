package com.omdmain.service;
import com.omdmain.pojo.AeonDelCommand;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:shenkunlin
 * @Description:AeonDelCommand业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface AeonDelCommandService {

    /***
     * AeonDelCommand多条件分页查询
     * @param aeonDelCommand
     * @param page
     * @param size
     * @return
     */
    PageInfo<AeonDelCommand> findPage(AeonDelCommand aeonDelCommand, int page, int size);

    /***
     * AeonDelCommand分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<AeonDelCommand> findPage(int page, int size);

    /***
     * AeonDelCommand多条件搜索方法
     * @param aeonDelCommand
     * @return
     */
    List<AeonDelCommand> findList(AeonDelCommand aeonDelCommand);

    /***
     * 删除AeonDelCommand
     * @param id
     */
    void delete( id);

    /***
     * 修改AeonDelCommand数据
     * @param aeonDelCommand
     */
    void update(AeonDelCommand aeonDelCommand);

    /***
     * 新增AeonDelCommand
     * @param aeonDelCommand
     */
    void add(AeonDelCommand aeonDelCommand);

    /**
     * 根据ID查询AeonDelCommand
     * @param id
     * @return
     */
     AeonDelCommand findById( id);

    /***
     * 查询所有AeonDelCommand
     * @return
     */
    List<AeonDelCommand> findAll();
}
