package com.jd.search.mapper;

import java.util.List;

import com.jd.common.pojo.SearchItem;

public interface SearchItemMapper {

	List<SearchItem> getItemList();
	
	SearchItem getItemById(long itemId);
}
