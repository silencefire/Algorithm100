package demos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 主要记录LeeCode中的相关题目，暂无分类
 */
public class DemoA2 {
    public static void main(String[] args) {
        DemoA2 d2 = new DemoA2();
        d2.ti2();
    }
    /**
     * @description:  圆圈中最后剩下的数字
     * 0,1,···,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字（删除后从下一个数字开始计数）。求出这个圆圈里剩下的最后一个数字。
     * 解决方法：
     * 1.递归
     * 2.模拟链表
     * @author: zhenghm
     * @time: 2022/12/7
     */
    private void ti1(){
        System.out.println("ArrayList模拟循环链表，结果："+ti1Method1(10,17));
        System.out.println("数学迭代方式结果："+ti1Method2(10,17));
    }
    /*
     * ArrayList模拟循环链表，返回的是最终的元素值；
     * ArrayList的删除时间复杂度是O(N),删除n次，则复杂度是O(N^2)，性能不推荐，但是容易理解，不涉及算法；
     */
    private int ti1Method1(int n, int m){
        List<Integer> list = new ArrayList<>();
        for(int i = 0;i<n;i++){
            list.add(i);
        }
        int id = 0;
        //循环直到list的长度为1
        while(list.size() != 1){
            id =(id+m-1)%list.size();//循环的关键
            list.remove(id);
        }
        return list.get(0);
    }
    /*
    * n个数字的数字环中删掉第m个数字；
    * f(n,m)是迭代方程，结果是返回的最后剩余的元素的值（也就是本次n个数排列的时候的位置）；
    * f(n-1,m)如果结果是x，那么f(n,m)=(m%n+x)%n = (m%n + f(n-1,m))%m
    * 即：f(n,m)在删掉一次元素后，元素变为(n-1)个，从(m%n)的位置处重新开始计数进行第二轮删除，
    * 这时的删除起始位置已经不是0了，而是m%n，而f(n-1,m)的结果又知道，所以直接能得到结果(m%n +f(n-1,m))%m
    * 而f(1,m)的结果是0，
    * 使用了动态规划法，迭代思想；时间复杂度：O(N)，空间复杂度：O(N)
    * */
    private int ti1Method2(int n,int m){
        if(n == 1){
            return 0;//仔细理解为什么是0，因为1个数在本次排列中，无论怎么删除，最后剩下的肯定是0位置的这个数；
        }
        return (m%n + ti1Method2(n-1,m))%n;//可以优化为(m+ti1Method2(n-1,m))%n，为了容易懂，就不简化了
    }
    /**
     * @description: 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * 解题方法：滑窗法，
     * @author: zhenghm
     * @time: 2022/12/8
     */
    private void ti2(){
        //采用滑窗算法
        System.out.println(getStringLength("tmmmzuxt"));
    }

    /**
     * 设置两个游标，一个start，一个end
     * 一个思路：用start向后遍历，end随之变动，时间复杂度为O(N^2)
     * 一个更好的思路：用end向后遍历，start不断调整，时间复杂度为O(N)，这里注意需要处理之前存在的数据：例如字符串为："tmmmzuxt"，
     * 在校验的时候，需要校验其下表是否大于start，大于才有效；
     */
    private int getStringLength(String str){
        int strMaxLength = 0,start=0,end=0;
        Map<Character,Integer> maps = new HashMap<>();
        for(;end<str.length();end++){
            char c = str.charAt(end);
            if(maps.containsKey(c) && maps.get(c)>=start){
                start = maps.get(c)+1;
            }else{
                strMaxLength = Math.max(end-start+1,strMaxLength);
            }
            maps.put(c,end);
        }
        return strMaxLength;
    }
}
