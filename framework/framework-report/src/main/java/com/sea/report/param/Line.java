package com.sea.report.param;

import java.util.List;

/**
 * 
 * @author sea
 *
 */
public class Line {
	
	
	/**
	 * 曲线名
	 */
	private String lineName;
	
	
	/**
	 * 曲线点
	 */
	private List<Point> points;

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	
	
}
