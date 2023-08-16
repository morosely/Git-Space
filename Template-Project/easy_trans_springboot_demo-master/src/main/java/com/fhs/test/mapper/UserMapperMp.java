package com.fhs.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fhs.core.trans.anno.AutoTrans;
import com.fhs.test.pojo.UserMp;

/**
 * 测试mybatis plus翻译
 */
@AutoTrans(namespace = "usermp",fields = "name",
        defaultAlias = "user",ref = UserMp.class,useCache = true)
public interface UserMapperMp extends BaseMapper<UserMp> {
}
