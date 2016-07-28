package com.jd.comment.util; 
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
 
/**
 * @author 码农小江
 * H20121012.java
 * 2012-10-12下午11:40:21
 */
public class ReadData {
    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static Map<String,String> readTxtFile(String filePath){
    	Map<String,String> ret = new HashMap<String,String>();
        try {
                String encoding="UTF-8";
                File file=new File(filePath);
               
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                     //   System.out.println(lineTxt);
                        String[] splitstr=lineTxt.split(" ");
                        ret.put(splitstr[0], splitstr[1]);
                    }
                    read.close();
                    
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
		
		return ret;
     
    }
     
    public static void main(String argv[]){
        String filePath1 = "C:\\Users\\zhanglei\\Desktop\\MiningResult1.txt";
        String filePath2 = "C:\\Users\\zhanglei\\Desktop\\MiningResult2.txt";
        String filePath3 = "C:\\Users\\zhanglei\\Desktop\\MiningResult3.txt";
//      "res/";
        Map<String,String> m1=readTxtFile(filePath1);//词条
        Map<String,String> m2=readTxtFile(filePath2);//饼状图
        Map<String,String> m3=readTxtFile(filePath3);//折线图
        
     //   System.out.println(readTxtFile(filePath1).size());
    }
     
     
 
}