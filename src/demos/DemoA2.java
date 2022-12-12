package demos;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 主要记录LeeCode中的相关题目，暂无分类
 */
public class DemoA2 {
    public static void main(String[] args) {
        DemoA2 d2 = new DemoA2();
        d2.ti5();
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

    /**
     * @description: 最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""
     * 思路一：遍历第一个字符串，然后遍历其他字符串，查看是否startWith，但是效率低，时间复杂度O(N)【用时4ms】
     * 思路二：遍历所有字符串，两两比较，每次比较出共同的前缀ans，然后ans再继续跟后面的字符串比较；这样ans的值就一直在减少；
     * 注意这里用for的普通循环比用for-each循环要好得多；节省没必要的循环也很重要；巧用break打断循环；【用时1ms】
     * @author: zhenghm
     * @time: 2022/12/9
     */
    private void ti3(){
        System.out.println("公共最长前缀："+longestCommonPrefix2(new String[]{"flower","flow","flight"}));
    }
    private String longestCommonPrefix1(String[] strs) {
        char[] chars = strs[0].toCharArray();
        StringBuilder sb = new StringBuilder();
        String longestStr = "";
        for (char aChar : chars) {
            sb.append(aChar);
            String temp = sb.toString();
            boolean res = true;//表示都匹配
            for (String s : strs) {
                if (!s.startsWith(temp)) {
                    res = false;
                    break;
                }
            }
            if (res) {
                longestStr = temp;
            }
        }
        return longestStr;
    }
    private String longestCommonPrefix2(String[] strs) {
        String ans = strs[0];
        for(int i=1;i<strs.length;i++){
            int j=0;
            for(;j<strs[i].length() && j<ans.length();j++){
                boolean isSame = strs[i].charAt(j) == ans.charAt(j);
                if(!isSame){
                    break;
                }
            }
            ans = ans.substring(0,j);
            if("".equals(ans)){
                return ans;
            }
        }
        return ans;
    }

    /**
     * @description: 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
     * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
     * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
     *方法一：使用split，然后翻转
     *方法二：参考官方解法，使用api：包括：字符串分数组split，数组转list（在int数组时慎不能用，要包装Integer），集合工具类的使用，String类的静态方法；
     * 个人看了一些解法：发现没有特别好的，效率基本差不太多，都是O(N)，所以暂时没必要纠结用api还是自己手动写细节了；
     * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
     * @author: zhenghm
     * @time: 2022/12/10
     */
    private void ti4(){
        System.out.println(reverseWords(" the sky is blue "));//注意这种前后都有空格的情况
        System.out.println(reverseWords2(" the sky is blue "));
    }
    private String reverseWords(String s) {
        String[] ss = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for(int i=ss.length-1;i>0;i--){
            sb.append(ss[i]).append(" ");
        }
        sb.append(ss[0]);
        return sb.toString();
    }
    private String reverseWords2(String s) {
        String[] strs = s.trim().split("\\s");
        List<String> sa = Arrays.asList(strs);
        Collections.reverse(sa);
        return String.join(" ",sa);
    }
    
    /**
     * @description:  给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序
     * @author: zhenghm
     * @time: 2022/12/11
     *
     * 第一种解法：用java的api，先找到排好序的数组，然后比对前后，但是效率最低   时间：19ms
     * 第二种解法：模拟冒泡排序，将需要排序的算进去，这个更慢...时间：344ms
     * 第三种解法：对第一种解法的优化，使用whhile替代for实现中断，使用了System.copy来复制数组，不适用原有数组地址
     * 做这种题可以把部分特殊的情况排除掉，不用做复杂的操作，会快很多；比如代码块里的；
     * 第四种做法：跟解法二的思想类似，但是不用api，全部简化为一个for循环；思想如下：（用到了贪心思想，不断找最大/小值）
     *  将数组分为三部分，A,B,C  其中B就是连续子数组；
     *  那么A中的所有元素的最大值，比B中的所有元素都小于等于；
     *  同理C中的所有元素的最小值，比B中的所有元素都大于等于；
     *  所以设定两个游标s1和s2，分别从数组的前后遍历（这里可以用一个for循环完成，下表需要控制）；
     *  从前向后，确定比最大值小的最后一个元素的位置，确定B数组的右边界；
     *  从后向前，确定比最小值大的最后一个元素的位置，确定B数组的左边界；
     *  去顶之后，相减就是B部分
     */
    private void ti5(){
        System.out.println("连续子数组大小为："+findUnsortedSubarray4(new int[]{2,6,4,8,10,9,15}));
        System.out.println("连续子数组大小为："+findUnsortedSubarray4(new int[]{1,2,3,3}));
    }
    private int findUnsortedSubarray(int[] nums){
        int[] temp = new int[nums.length];
        int c=0;
        for(int in : nums){
            temp[c++] = in;
        }
        Arrays.sort(temp);
        List<Integer> list = Arrays.stream(temp).boxed().collect(Collectors.toList());//int[]转arrays必用
        for(int i=0;i<nums.length;i++){
            if(list.get(0)==nums[i]){
                list.remove(0);
            }else{
                break;
            }
        }
        if(list.size()!=0){
            for(int j=nums.length-1;j>=0;j--){
                if(list.get(list.size()-1) == nums[j]){
                    list.remove(list.size()-1);
                }else{
                    break;
                }
            }
        }
        return list.size();
    }
    private int findUnsortedSubarray2(int[] nums){
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<nums.length;j++){
                if(!((i>=j && nums[i]>=nums[j]) || (i<j && nums[i]<=nums[j]))){
                    list.add(i);
                    break;
                }
            }
        }
        if(list.size()>1){
            return Collections.max(list)-Collections.min(list)+1;
        }
        return list.size();
    }
    private int findUnsortedSubarray3(int[] nums){
        //这里可以写成一个工具方法，为了整体性，就写在一起了，放在了代码块里
        {
            boolean isSorted = true;
            for(int i=0;i<nums.length-1;i++){
                if(nums[i]>nums[i+1]){
                    isSorted = false;
                    break;
                }
            }
            if(isSorted){
                return 0;
            }
        }

        int[] numsSorted = new int[nums.length];
        System.arraycopy(nums,0,numsSorted,0,nums.length);
        Arrays.sort(numsSorted);
        int start = 0;
        while(nums[start] == numsSorted[start]){
            start++;
        }
        int end = nums.length-1;
        while(nums[end] == numsSorted[end]){
            end--;
        }
        return end-start+1;
    }
    private int findUnsortedSubarray4(int[] nums){
        int minnum = Integer.MAX_VALUE,start = -1;
        int maxnum = Integer.MIN_VALUE,end = -1;
        for(int i=0;i<nums.length;i++){
            if(maxnum>nums[i]){
                end = i;//遍历整个数组找到最后一个，比最大值小的元素，这个就是end；从前往后找最大的元素；
            }else{
                maxnum = nums[i];//不断更新最大的元素
            }
            if(minnum<nums[nums.length-i-1]){//从后往前遍历，找到最后一个，比最小值大的元素。这个就是start;
                start = nums.length-i-1;
            }else{
                minnum = nums[nums.length-i-1];
            }
        }
        return end == -1 ?0 : end-start+1;
    }
}
