package com.ltxom.old;

import java.util.ArrayList;

public class HtmlPage
{
	private int StartAt = 218;
	ArrayList<String> codes;

	String city;

	String currentDate;// ~
	String currentTemp;// ~
	String currentWind;// ~
	String currentWeather;// ~

	String nextPhaseDate;// ~
	String nextPhaseTemp;
	String nextPhaseWeather;// ~
	String nextPhaseWind;// ~

	HtmlPage(String url)
	{
		while (true)
		{
			codes = Kits.getCodes(url);
			// System.out.println(codes);
			if (!codes.equals(null))
			{
				Initialization();
				break;
			}
		}
	}

	private void Initialization()
	{
		boolean todayFlag = true;
		for (; StartAt < codes.size(); StartAt++)
		{
			/***********************/
			String nowLine = codes.get(StartAt);
			if (nowLine.contains("hidden_title"))
			{
				String temp = nowLine.split("\"")[5];
				String info[] = temp.split(" ");
				currentDate = info[0] + "\t" + info[1];
				currentWeather = info[3];
				currentTemp = info[5];
			}

			/**********************/
			if (nowLine.contains("<div class=\"slid\"></div>"))
			{
				String temp = codes.get(StartAt + 3);
				nextPhaseDate = temp.replace("<h1>", "").replace("</h1>", "");
			}

			/*********************/
			if (!todayFlag && nowLine.contains("<p class=\"win\">"))
			{
				String temp = codes.get(StartAt);

				nextPhaseWind = temp.split("\"")[7];
				nextPhaseWind += " " + temp.split("\"")[8].replace("</span></p>", "").replace(">", "");
			}

			/***********************/
			if (!todayFlag && nowLine.contains("<p class=\"wea\" title=\""))
			{
				nextPhaseWeather = nowLine.split("\"")[4].replace("</p>", "").replace(">", "");
			}
			if (!todayFlag && nowLine.contains("<p class=\"tem\">"))
			{
				nextPhaseTemp = codes.get(StartAt + 1).replace("<span>", "").replace("</span><em>°C</em>", "");
			}

			if (todayFlag && nowLine.contains("<p class=\"win\">"))
			{
				String temp = codes.get(StartAt + 2);
				currentWind = temp.split("\"")[3];
				currentWind += " " + temp.split("\"")[4].replace("</span>", "").replace(">", "");
				todayFlag = false;
			}
			if (nowLine.contains("observe24h_data"))
			{
				city = nowLine.split("\"")[9];
				return;
			}
		}
	}
}
