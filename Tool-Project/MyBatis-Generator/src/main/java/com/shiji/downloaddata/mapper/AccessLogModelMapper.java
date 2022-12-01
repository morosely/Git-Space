package com.shiji.downloaddata.mapper;

import com.shiji.downloaddata.model.AccessLogModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccessLogModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessLogModel record);

    int insertSelective(AccessLogModel record);

    AccessLogModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccessLogModel record);

    int updateByPrimaryKey(AccessLogModel record);
}