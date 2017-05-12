package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class FileReaderUtil{
	FileReader fr = null;
	BufferedReader br = null;
	
	public FileReaderUtil(String fileName) {
		// System.out.println(fileName);
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
		}
		catch (FileNotFoundException e) {
			System.out.println(">>> Error at no more instruction.");
		}
	}
	
	public void close() {
		try {
			if (br != null) br.close();
			if (fr != null) fr.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 函数功能：该函数将文件urlIn总的数据一行一行读进内存data中,读完删除文件
     *
     * @param urlIn：输入的数据的路径，
     * @param data:存储ArrayList<String>数据的结构体 :
     */
    public static void readFileToList(String urlIn, List<String> data) {
        long numOfLinesList = 0;//用于readFileToList打log
        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlIn));//数据读取流
            br = new BufferedReader(isr);//定义数据读取流
            String eachLine = "";//定义一个字符串用来暂存读取的一行数据
            while ((eachLine = br.readLine()) != null) {
                if (numOfLinesList % 10000 == 0) {
                    System.out.println("=Read File to List is still running: num = " + numOfLinesList / 10000 + "万。"); //打log
                }
                numOfLinesList++;
                if (eachLine.equals("")) { //跳过空行
                    continue;
                }
                eachLine = eachLine.trim();
                data.add(eachLine.trim());//去掉两边的空格，存入data中
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }

}
