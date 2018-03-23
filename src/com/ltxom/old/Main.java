package com.ltxom.old;

import java.io.*;


public class Main
{
	private static String message;

	static ExtraMessage em;
	private static String extra = "";
	private static String anwser = "";
	private static String url = "";
	public static String commandList = "/send: 发送至所有接收者send to all receiver" + "\n\n"
			+ "/info: 显示天气信息display weather infomations" + "\n\n"
			+ "/update: 更新天气信息到内存中update all weather information to RAM" + "\n\n" + "/help: 显示帮助列表ask for helping"
			+ "\n\n" + "/remain: 剩余消息数" + "\n\n" + "/remainDetail: 显示所有剩余消息" + "\n\n" + "/used：显示已使用的句子" + "\n\n"
			+ "/updateUrl [网址]: 更新网址" + "\n\n" + "/reset 更新天气与句子" + "\n\n" + "/setTime 时:分 设置自动发送的时间"+"\n\n" + "/stop 停止程序";
	
	// 邮箱服务器地址
	static final String HOST = "host.xxx.com";
	
	//发送邮件账户与密码
	static final String EMAIL = "example@xxx.com";
	static final String PASSWORD = "password";
	
	//接收账户
	static final String RECEIVE_EMAIL = "example@xxx.com";
	
	//定义自动发送消息的时间，此处为6:00 AM
	private static int hour = 6;
	private static int min = 0;

	//发送天气的城市请在Url.txt中定义
	public static void main(String[] args) throws Exception
	{
		em = new ExtraMessage("res/List.txt");
		com_updateUrl();

		System.out.println(url);
		System.out.println("初始化中……");
		reset();

		new Thread(new Command()).start();

		System.out.println("初始化完毕。");
		while (true)
		{
			Thread.sleep(1000 * 60);// 60s
			if ((new Integer(Kits.getTime().split(":")[0]).equals(hour)
					&& (new Integer(Kits.getTime().split(":")[1]).equals(min))))
			{// 若为上午6:00
				com_update();
				com_send();
				reset();
			}
		}

	}

	static void com_send() throws Exception
	{
		send(url, RECEIVE_EMAIL);
	
	}

	static void com_info()
	{
		System.out.println(message.replaceAll("<br>", "\n"));
	}

	static void com_update()
	{
		System.out.println("-------------更新天气数据中：");
		resetWeatherInfo();
		System.out.println("-------------更新完毕-------------");

	}

	static void com_help()
	{
		System.out.println("支持的命令如下：");
		System.out.println(Main.commandList);
	}

	static void com_remain()
	{
		int usedList[] = loadUsedNum();
		System.out.println("剩余：" + (em.getLenth() - (usedList.length - 1)));
	}

	static void com_usedDetail()
	{
		int usedList[] = loadUsedNum();
		System.out.println("已使用数量:" + usedList.length);
		System.out.println("已使用详细信息(下标):");
		for (int i : usedList)
		{
			System.out.print(i + ",");
		}
	}

	static void com_remainDetail()
	{
		int usedList[] = loadUsedNum();
		System.out.println("剩余详细信息(下标):");
		a: for (int i = 0; i < em.getLenth(); i++)
		{
			for (int j : usedList)
			{
				if (j == i)
				{
					continue a;
				}

			}
			System.out.print(i + ",");
		}
	}

	static void com_updateUrl()
	{
		url = ExtraMessage.readFileByChars("res/Url.txt");
	}

	static void com_updateUrl(String u)
	{
		url = u;
	}

	static void com_setTime(String time)
	{
		System.out.println("原先设置的时间为：" + hour + ":" + min);
		hour = new Integer(time.split(":")[0]);
		min = new Integer(time.split(":")[1]);
		System.out.println("已设置为：" + hour + ":" + min);
	}

	static void send(String url, String address) throws Exception
	{
		try
		{
			Sendmail.sendMail(address, "帅的人的神秘消息", message);
		} catch (Exception e)
		{
			Sendmail.sendMail(address, "帅的人的神秘消息", message);
		}
	}

	static String extraAnwser = "";

	static void reset()
	{// 深圳
		HtmlPage page = new HtmlPage(url);
		// System.out.println("s");
		// HtmlPage page2 = new
		// HtmlPage("http://www.weather.com.cn/weather1d/101280601.shtml");

		if (anwser.contains("答案："))
		{
			extraAnwser = "<br>" + anwser;
			anwser = "";
		} else
		{
			extraAnwser = "";
		}

		setTomorrowExtraMessage();
		message = "城市：" + page.city + "<br>时间：" + page.currentDate + "<br>现在温度：" + page.currentTemp + "<br>风速："
				+ page.currentWind + "<br>天气：" + page.currentWeather + "<br> <br>" + page.nextPhaseDate + "天气：<br>风速："
				+ page.nextPhaseWind + "<br>天气：" + page.nextPhaseWeather + "<br>温度：" + page.nextPhaseTemp + "<br><br>"
				+ extra + extraAnwser;
		com_info();

	}

	static void resetWeatherInfo()
	{
		HtmlPage page = new HtmlPage(url);

		message = "城市：" + page.city + "<br>时间：" + page.currentDate + "<br>现在温度：" + page.currentTemp + "<br>风速："
				+ page.currentWind + "<br>天气：" + page.currentWeather + "<br> <br>" + page.nextPhaseDate + "天气：<br>风速："
				+ page.nextPhaseWind + "<br>天气：" + page.nextPhaseWeather + "<br>温度：" + page.nextPhaseTemp + "<br><br>"
				+ extra + extraAnwser;
		//com_info();

	}

	static void setTomorrowExtraMessage()
	{

		a: while (true)
		{
			try
			{
				int usedList[] = loadUsedNum();
				if (usedList.length - 1 == em.getLenth())
					break;

				int randomIndex = 0 + (int) (Math.random() * em.getLenth());

				for (int x : usedList)
				{
					if (randomIndex == x)
					{
						continue a;
					}
				}

				if (em.isQue(randomIndex))
				{
					String origin = em.get(randomIndex);
					extra = origin.split("答案：")[0];
					anwser = "昨日问题：" + extra + "<br>答案：" + origin.split("答案：")[1];

					// System.out.println(extra);
					// System.out.println(anwser);
					// System.out.println(randomIndex + ":-----问题-----" + );
				} else
				{
					extra = em.get(randomIndex).replace("\n", "<br>");
				}

				ExtraMessage.appendFile("res/Used.txt", randomIndex + ",");

				break;
			} catch (NumberFormatException e)
			{
				ExtraMessage.appendFile("res/Used.txt", "-1,");

			}
		}
	}

	static int[] loadUsedNum()
	{

		String temp[] = ExtraMessage.readFileByChars("res/Used.txt").split(",");
		int[] arr = new int[temp.length];
		int counter = 0;
		for (String x : temp)
		{
			arr[counter] = new Integer(x);
			counter++;
		}
		return arr;
	}
}

class Command implements Runnable
{

	@Override
	public void run()
	{
		while (true)
		{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line;
			try
			{
				line = br.readLine();
				if (!line.startsWith("/"))
					throw new IOException();
				else if (line.startsWith("/send"))
				{
					Main.com_send();
				} else if (line.startsWith("/info"))
				{
					Main.com_info();
				} else if (line.startsWith("/updateUrl http://"))
				{
					Main.com_updateUrl(line.replace("/updateUrl ", ""));
				} else if (line.startsWith("/updateUrl"))
				{
					Main.com_updateUrl();
				} else if (line.startsWith("/update"))
				{
					Main.com_update();
				} else if (line.startsWith("/help"))
				{
					Main.com_help();
				} else if (line.startsWith("/remainDe") || line.startsWith("/remainde"))
				{
					Main.com_remainDetail();
				} else if (line.startsWith("/remain"))
				{
					Main.com_remain();
				} else if (line.startsWith("/used"))
				{
					Main.com_usedDetail();
				} else if (line.startsWith("/reset"))
				{
					Main.reset();
				} else if (line.startsWith("/setTime "))
				{
					Main.com_setTime(line.replace("/setTime ", ""));
				} else if (line.startsWith("/stop"))
				{
					System.exit(0);
				}else if (line.startsWith("/haha"))
				{
					System.out.println("LOL");
				}else
				{
					throw new IOException();
				}

			} catch (IOException e)
			{
				System.out.println("无效指令，支持的指令如下：");
				System.out.println(Main.commandList);
			} catch (Exception e)
			{
				System.out.println("报错：");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
