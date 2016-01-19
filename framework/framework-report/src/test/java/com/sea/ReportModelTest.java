package com.sea;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sea.report.hchart.LineChart4HChart;
import com.sea.report.param.Line;
import com.sea.report.param.LineChart;
import com.sea.report.param.Point;

public class ReportModelTest
{

	
	public void checkJsonDataFormat()
	{
		LineChart lineChart = new LineChart();
		lineChart.setTitle("报表");
		lineChart.setSubTitle("曲线图");
		lineChart.setValueSuffix("。C");
		lineChart.setyTitle("温度");

		List<Line> lines = new ArrayList<Line>();
		Line line = new Line();
		line.setLineName("福州");

		List<Point> points = new ArrayList<Point>();
		Point point = new Point("九月", 1);
		points.add(point);
		points.add(point);
		points.add(point);
		points.add(point);
		points.add(point);
		points.add(point);
		points.add(point);
		points.add(point);
		points.add(point);

		line.setPoints(points);
		lines.add(line);
		lines.add(line);
		lines.add(line);
		lines.add(line);
		lines.add(line);
		lines.add(line);
		lines.add(line);

		lineChart.setLines(lines);

		LineChart4HChart lineChart4HChart = lineChart.convertLineBase4HChart(lineChart);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		System.out.println(gson.toJson(lineChart4HChart));

	}

}
