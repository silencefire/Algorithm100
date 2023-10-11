package com.algorithm.util;

import java.io.File;
import java.io.IOException;


/**
 * 主要作用：记录每个工具类的作用，写个目录，方便检索
 */
public class FileDirectoryUtils {
    public static void main(String[] args) throws IOException {
        FileDirectoryUtils fnt = new FileDirectoryUtils("E:\\SourceCode\\github_me\\Algorithm100\\src\\com\\algorithm\\demos");
        fnt.doThings();
        //System.out.println("ode/github_me/LearnNote/src/main/java".length());
    }

    File f;

    /**
     * 设置要输出的目录路径
     * @param path 文件路径
     */
    public FileDirectoryUtils(String path) {
        f = new File(path);
    }

    /**
     * 入口
     */
    public void doThings() throws IOException {
        printFile(f,0);
    }

    /**
     * @param dirc 文件路径
     * @param i 文件层数
     * @throws IOException
     */
    public void printFile(File dirc,int i) throws IOException {
        i++;
        //若是目录
        if (testDir(dirc)) {
            //目录也要打印
            printDircName(dirc,i);
            //列出子文件
            File[] list = dirc.listFiles();
            //遍历，并判断是否是文件|文件夹
            for(File file : list){
                printFile(file,i);
            }
        }else{
            //非目录，直接打印文件名称
            printFileName(dirc,i);
        }
    }

    /**
     * 生成md格式内容
     * @param dirc 文件
     */
    public void printDircName(File dirc, int i) {
        StringBuilder sb = new StringBuilder();
        for(int j=1;j<i;j++){
            sb.append("  ");
        }
        sb.append("* ").append(dirc.getName()).append("[").append("]");
        System.out.println(sb);
    }

    /**
     * 生成md格式内容
     * @param dirc 文件
     */
    public void printFileName(File dirc, int i) {
        StringBuilder sb = new StringBuilder();
        for(int j=1;j<i;j++){
            sb.append("  ");
        }
        String filepath =
                dirc.getAbsolutePath()
                        .replaceAll("\\\\","/")
                        .substring(System.getProperty("user.dir").length()+4);
        sb.append("* ").append("[").append(dirc.getName()).append("]")
                .append("(").append(filepath).append(")");
        System.out.println(sb);
    }

    /**
     * 是不是个目录
     * @return
     */
    public boolean testDir(File dirc) {
        return dirc.isDirectory();
    }
}
