package com.turing.b2c.model.dto;

import com.turing.b2c.model.pojo.Brand;
import com.turing.b2c.model.pojo.union.SpecificationUnion;

import java.io.Serializable;
import java.util.List;

public class SearchResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long total;
	private List<T> list;
	private  List<String> categoryList;
	private  List<Brand> brandList;
	private  List<SpecificationUnion> specUnionList;

	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	public List<SpecificationUnion> getSpecUnionList() {
		return specUnionList;
	}

	public void setSpecUnionList(List<SpecificationUnion> specUnionList) {
		this.specUnionList = specUnionList;
	}

	public SearchResult() {
	}

	public SearchResult(Long total, List<T> list) {
		this.total = total;
		this.list = list;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
