package com.ltxom.old;

import java.io.*;
import java.util.*;


public class ExtraMessage
{
	/*public static void main(String args[])
	{
		String text = readFileByChars("/Users/ltxom/Desktop/head trun around fastly.txt");
		String textArg[] = text.split("\n");

		for (int i = 0; i < textArg.length; i++)
		{
			if (i % 2 == 0)
			{
				textArg[i] = "\n|\n" + textArg[i].replace("　　", "").replace((i / 2 + 1) + "", "");
			}
			System.out.println(textArg[i]);
		}

	}
*/
	public static void main(String args[])
{
		ExtraMessage em = new ExtraMessage("res/list.txt");
		System.out.println(em.getLenth());
		System.out.println(em.get(123));
		System.out.println(em.isQue(123));
}

	
	public static String text = null;
	static HashMap<Integer,String> listMap = new HashMap<Integer,String>();

	public ExtraMessage(String path){
		text = readFileByChars(path);
		String list[] = text.split("=");

		for (int i = 0; i < list.length; i++)
		{
			listMap.put(i, list[i]);
		}

	}
	
	public int getLenth(){
		return listMap.size()-1;
	}
	public String get(int index){
		return listMap.get(index);
	}
	
 	public boolean isQue(int index){
 		if(listMap.get(index).contains("答案："))
 			return true;
 		return false;
 	}
	
	public static String readFileByChars(String fileName)
	{
		File file = new File(fileName);
		Reader reader = null;
		StringBuilder sb = new StringBuilder("");
		try
		{
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1)
			{
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r')
				{
					sb.append((char) tempchar);
				}

			}
			reader.close();
			return sb.toString();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void appendFile(String fileName, String content) {   
        FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(fileName, true);     
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    }     
}
