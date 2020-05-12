package com.turing.b2c.model.dto;

import java.io.Serializable;

public class SearchParam implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer pageNum;
	private Integer pageSize;

	public String getCatgory() {
		return catgory;
	}

	public void setCatgory(String catgory) {
		this.catgory = catgory;
	}

	private  String catgory;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	private String keyword;

	public SearchParam() {
	}

	public SearchParam(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
