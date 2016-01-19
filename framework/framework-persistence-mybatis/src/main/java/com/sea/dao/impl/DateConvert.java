package com.sea.dao.impl;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

public class DateConvert implements Converter
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object convert(Class arg0, Object arg1)
	{
		String p = (String) arg1;
		if ((p == null) || (p.trim().length() == 0))
			return null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(p.trim());
		} catch (Exception e)
		{
		}
		return null;
	}
}