package com.ltxom.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
//import java.util.Date;
import java.util.Calendar;
//import java.text.SimpleDateFormat;

public class Kits
{
	/**
	 * 得到网页的代码
	 */
	static ArrayList<String> getCodes(String url)
	{
		ArrayList<String> list = null;
		while (list == null)
		{
			try
			{
				URL u = new URL(url);
				URLConnection conn = u.openConnection();

				conn.setRequestProperty("User-Agent", "Mozilla/5.0");
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

				String line = null;

				list = new ArrayList<String>();
				while ((line = br.readLine()) != null)
				{
					list.add(line);

				}
				return list;

			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				System.out.println("-------url地址不合法，已跳过-------");
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				System.out.println("-------IO异常，页面可能不存在，已跳过-------");
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 返回当前时间：二十四小时制
	 * 
	 * @return String类型，如：『14:23:33』
	 */
	static String getTime()
	{
		// Date now = new Date();
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd
		// HH:mm:ss");// 可以方便地修改日期格式

		// String hehe = dateFormat.format(now);
		// System.out.println(hehe);

		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改

		// int year = c.get(Calendar.YEAR);
		// int month = c.get(Calendar.MONTH);
		// int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String time = hour + ":" + minute + ":" + second;
		return time;
	}
}
