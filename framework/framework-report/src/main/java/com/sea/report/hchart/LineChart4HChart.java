package com.sea.report.hchart;

import java.util.List;


/**
 * 
 * @author sea
 *
 */
public class LineChart4HChart {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 图副标题
	 */
	private String subTitle;

	/**
	 * x轴分类信息
	 */
	private List<Object> xCategories;

	/**
	 * y 标题
	 */
	private String yTitle;

	/**
	 * y轴值尾缀
	 */
	private String valueSuffix;

	/**
	 * 数据系列
	 */
	private List<Line4HChart> series;
	
	
	

	public String getSubTitle()
	{
		return subTitle;
	}

	public void setSubTitle(String subTitle)
	{
		this.subTitle = subTitle;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String tittle)
	{
		this.title = tittle;
	}

	public List<Object> getxCategories()
	{
		return xCategories;
	}

	public void setxCategories(List<Object> xCategories)
	{
		this.xCategories = xCategories;
	}

	public String getyTitle()
	{
		return yTitle;
	}

	public void setyTitle(String yTitle)
	{
		this.yTitle = yTitle;
	}

	public String getValueSuffix()
	{
		return valueSuffix;
	}

	public void setValueSuffix(String valueSuffix)
	{
		this.valueSuffix = valueSuffix;
	}

	public List<Line4HChart> getSeries()
	{
		return series;
	}

	public void setSeries(List<Line4HChart> series)
	{
		this.series = series;
	}

}
