package com.jd.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{
	
	private List<SearchItem> itemList;
	private long recordCount;
	private long pageCount;
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public long getPageCount() {
		return pageCount;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
}
