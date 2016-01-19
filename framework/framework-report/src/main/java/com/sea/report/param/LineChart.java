package com.sea.report.param;

import java.util.ArrayList;
import java.util.List;

import com.sea.report.hchart.Line4HChart;
import com.sea.report.hchart.LineChart4HChart;

/**
 * 
 * @author sea
 *
 */
public class LineChart {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 图副标题
	 */
	private String subTitle;


	/**
	 * y 标题
	 */
	private String yTitle;

	/**
	 * y轴值尾缀
	 */
	private String valueSuffix;

	/**
	 * 多条曲线点
	 */
	private List<Line> lines;

	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String tittle) {
		this.title = tittle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTittle) {
		this.subTitle = subTittle;
	}



	public String getyTitle()
	{
		return yTitle;
	}

	public void setyTitle(String yTitle)
	{
		this.yTitle = yTitle;
	}

	public String getValueSuffix() {
		return valueSuffix;
	}

	public void setValueSuffix(String valueSuffix) {
		this.valueSuffix = valueSuffix;
	}

	/**
	 * 
	 * 转换为hchart数据结构
	 * 
	 * @param lineChart
	 * @return
	 */
	public LineChart4HChart convertLineBase4HChart(LineChart lineChart) {

		LineChart4HChart lineChar4HChart = new LineChart4HChart();
		lineChar4HChart.setTitle(lineChart.getTitle());
		lineChar4HChart.setSubTitle(lineChart.getSubTitle());
		lineChar4HChart.setValueSuffix(lineChart.getValueSuffix());
		lineChar4HChart.setyTitle(lineChart.getyTitle());

		List<Line> lines = lineChart.getLines();
		
		
		this.checkDataFormat(lines);

		List<Object> categorieList = new ArrayList<Object>();

		List<Line4HChart> series = new ArrayList<Line4HChart>();
		for (int i = 0; i < lines.size(); i++) {
			List<Point> points = lines.get(i).getPoints();
			if (i == 0) {
				for (Point point : points) {
					categorieList.add(point.getX());
				}
			}

			Line4HChart line4HChart = new Line4HChart();
			line4HChart.setName(lines.get(i).getLineName());

			List<Object> values = new ArrayList<Object>();
			for (Point point : lines.get(i).getPoints()) {
				values.add(point.getY());
			}
			line4HChart.setData(values);
			series.add(line4HChart);

		}
		lineChar4HChart.setxCategories(categorieList);

		lineChar4HChart.setSeries(series);

		return lineChar4HChart;
	}

	
	/**
	 * 检验数据格式
	 * 1. 保证每条数据的数据点是一样多的 2. 保证每条数据的x轴是一样的。
	 * @param linePoints
	 */
	private void checkDataFormat(List<Line> linePoints) {

		int count = 0;
		List<Object> xList = new ArrayList<Object>();
		for (Line line : linePoints) {

			if (count == 0) {
				count = line.getPoints().size();
				for (Point point : line.getPoints()) {
					xList.add(point.getX());
				}
				continue;
			}

			if (count == line.getPoints().size()) {
				for (int i = 0; i < xList.size(); i++) {
					if (xList.get(i).equals(line.getPoints().get(i).getX())) {

					} else {
						throw new RuntimeException("x数据不一致！");
					}
				}
			} else {
				throw new RuntimeException("各个曲线列数不一致！");
			}

		}

	}

}
