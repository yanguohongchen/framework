package com.sea.report.param;

/**
 * 
 * @author sea
 *
 */
public class Point
{

	/**
	 * x值
	 */
	private Object x;

	/**
	 * y值
	 */
	private Object y;

	public Point(Object x, Integer y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	public Point(Object x, Long y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point(Object x, Double y)
	{
		this.x = x;
		this.y = y;
	}

	public Object getX()
	{
		return x;
	}

	public void setX(Object x)
	{
		this.x = x;
	}

	public Object getY()
	{
		return y;
	}

	public void setY(Object y)
	{
		this.y = y;
	}

}
