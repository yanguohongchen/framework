package com.sea.report.hchart;

import java.util.List;

/**
 * 
 * @author sea
 *
 */
public class Line4HChart {

	/**
	 * 曲线名称
	 */
	private String name;

	/**
	 * 曲线数据
	 */
	private List<Object> data;


	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
