package com.omdmain.service.impl;
import com.omdmain.dao.AeonDelCommandMapper;
import com.omdmain.pojo.AeonDelCommand;
import com.omdmain.service.AeonDelCommandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:shenkunlin
 * @Description:AeonDelCommand业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class AeonDelCommandServiceImpl implements AeonDelCommandService {

    @Autowired
    private AeonDelCommandMapper aeonDelCommandMapper;


    /**
     * AeonDelCommand条件+分页查询
     * @param aeonDelCommand 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<AeonDelCommand> findPage(AeonDelCommand aeonDelCommand, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(aeonDelCommand);
        //执行搜索
        return new PageInfo<AeonDelCommand>(aeonDelCommandMapper.selectByExample(example));
    }

    /**
     * AeonDelCommand分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<AeonDelCommand> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<AeonDelCommand>(aeonDelCommandMapper.selectAll());
    }

    /**
     * AeonDelCommand条件查询
     * @param aeonDelCommand
     * @return
     */
    @Override
    public List<AeonDelCommand> findList(AeonDelCommand aeonDelCommand){
        //构建查询条件
        Example example = createExample(aeonDelCommand);
        //根据构建的条件查询数据
        return aeonDelCommandMapper.selectByExample(example);
    }


    /**
     * AeonDelCommand构建查询对象
     * @param aeonDelCommand
     * @return
     */
    public Example createExample(AeonDelCommand aeonDelCommand){
        Example example=new Example(AeonDelCommand.class);
        Example.Criteria criteria = example.createCriteria();
        if(aeonDelCommand!=null){
            // 
            if(!StringUtils.isEmpty(aeonDelCommand.getShopCode())){
                    criteria.andEqualTo("shopCode",aeonDelCommand.getShopCode());
            }
            // 
            if(!StringUtils.isEmpty(aeonDelCommand.getSql())){
                    criteria.andEqualTo("sql",aeonDelCommand.getSql());
            }
            // 
            if(!StringUtils.isEmpty(aeonDelCommand.getUpdateDate())){
                    criteria.andEqualTo("updateDate",aeonDelCommand.getUpdateDate());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete( id){
        aeonDelCommandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改AeonDelCommand
     * @param aeonDelCommand
     */
    @Override
    public void update(AeonDelCommand aeonDelCommand){
        aeonDelCommandMapper.updateByPrimaryKey(aeonDelCommand);
    }

    /**
     * 增加AeonDelCommand
     * @param aeonDelCommand
     */
    @Override
    public void add(AeonDelCommand aeonDelCommand){
        aeonDelCommandMapper.insert(aeonDelCommand);
    }

    /**
     * 根据ID查询AeonDelCommand
     * @param id
     * @return
     */
    @Override
    public AeonDelCommand findById( id){
        return  aeonDelCommandMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询AeonDelCommand全部数据
     * @return
     */
    @Override
    public List<AeonDelCommand> findAll() {
        return aeonDelCommandMapper.selectAll();
    }
}
