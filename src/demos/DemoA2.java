package demos;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 主要记录LeeCode中的相关题目，暂无分类
 */
public class DemoA2 {
    public static void main(String[] args) {
        DemoA2 d2 = new DemoA2();
        d2.ti8();
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
    
    /**
     * @description:  字符串的最大公因子/最大公约数
     * 对于字符串s和t，只有在s = t + ... + t（t 自身连接 1 次或多次）时，我们才认定“t 能除尽 s”。
     * 给定两个字符串str1和str2。返回 最长字符串x，要求满足x 能除尽 str1 且X 能除尽 str2
     * @author: zhenghm
     * @time: 2022/12/13
     *
     * 方法一：我的思路，使用api的replaceAll，但是效率极低；耗时887 ms；
     * 方法二：不用api，手工解决，思路：遍历较短的字符串，从后到前截取字符串，看是否可以除尽两个字符串的长度，用于排除大部分不符情况；
     * 比较的方法也比较有意思，用的不是切割原有字符串，而是拼接新的字符串然后进行对比，
     * 因为是从大到小遍历的，所以第一次遍历到的就是符合情况的，耗时1ms、
     * 值得学习的思想：采用数学的思想先剔除大部分不符合的情况，再用api来逐个对比，效率高了很多倍，而且从后往前遍历，第一遍历就出结果，不用遍历其他情况；
     * 方法三：使用两条性质，具体推理就不说了太多了，坦白说这两个性质都不懂，推理逻辑上感觉不通；先记下吧，需要的时候研究；
     * 性质1：如果存在一个符合要求的字符串 X，那么也一定存在一个符合要求的字符串 X'，它的长度为 str1 和 str2 长度的最大公约数
     *      用性质1不用遍历直接找到对应字符串，再用checkStringDivide校验；
     *      这个也叫做：“辗转相除法”，我理解就是利用递归；
     *      最大公约数公式： gcd(int a,int b)= b==0?a:gcd(b,a%b);//也就是如果存在最大公约数，就一定是这个长度，否则没有；
     * 性质2：：如果 str1 和 str2 拼接后等于 str2和 str1 拼接起来的字符串（注意拼接顺序不同），那么一定存在符合条件的字符串 X
     *      用性质2来避免checkStringDivide方法的验证，如果正反相等，则一定有最大公约数字符串x；
     */
    private void ti6(){
        System.out.println(gcdOfStrings2("ABCABC","ABC"));
        System.out.println(gcdOfStrings2("ABABABABAB","ABAB"));
        System.out.println(gcdOfStrings2("LEET","CODE"));
        System.out.println(gcd(3,8));
    }
    private String gcdOfStrings(String str1, String str2) {
        char[] chars = str1.length()<=str2.length() ? str1.toCharArray() : str2.toCharArray();
        StringBuilder sb = new StringBuilder();
        String str = "";
        String res = "";
        for(Character c : chars){
            sb.append(c);
            str = sb.toString();
            if(str1.replaceAll(str,"").length() == 0 && str2.replaceAll(str,"").length() == 0){
                res = str;
            }
        }
        return res;
    }

    private String gcdOfStrings2(String str1, String str2) {
        int len1 = str1.length(),len2 = str2.length();
        int length = Math.min(len1,len2);
        String str;
        for(int i = length;i>=1;i--){
            if(len1%i == 0 && len2%i == 0){  //用长度来过滤大部分不符合的情况，提高遍历效率
                str = str1.substring(0,i);
                if(checkStringDivide(str,str1) && checkStringDivide(str,str2)){
                    return str;
                }
            }
        }
        return "";
    }
    //用这个方法也比我的repalceall快很多；有空看下repalceAll的实现方法
    private boolean checkStringDivide(String strMin,String str){
        int num = str.length()/strMin.length();
        StringBuilder sb = new StringBuilder();
        for(int i=0 ;i<num;i++){
            sb.append(strMin);
        }
        if(str.equals(sb.toString())){
            return true;
        }else{
            return false;
        }

    }

    //方法三
    private String gcdOfStrings3(String str1, String str2){
        int len1 = str1.length(), len2 = str2.length();
        String T = str1.substring(0, gcd(len1, len2));
        if (checkStringDivide(T, str1) && checkStringDivide(T, str2)) {
            return T;
        }
        return "";
    }
    private int gcd(int a,int b){ //最大公约数
        return b==0?a:gcd(b,a%b);
    }
    //方法四
    private String gcdOfStrings4(String str1, String str2){
        if (!str1.concat(str2).equals(str2.concat(str1))) {
        return "";
    }
        return str1.substring(0, gcd(str1.length(), str2.length()));
    }

    /** 残血状态回归，阳了三天，抓紧补上一部分
     * @description: 有效括号的嵌套度
     * 有效括号字符串 定义：对于每个左括号，都能找到与之对应的右括号
     * 嵌套深度 depth 定义：即有效括号字符串嵌套的层数
     * 给你一个「有效括号字符串」 seq，请你将其分成两个不相交的有效括号字符串，A 和 B，并使这两个字符串的深度最小
     *   不相交：每个 seq[i] 只能分给 A 和 B 二者中的一个，不能既属于 A 也属于 B 。
     *   A 或 B 中的元素在原字符串中可以不连续。
     *   A.length + B.length = seq.length
     *   深度最小：max(depth(A), depth(B)) 的可能取值最小
     *
     * 解决思路：让连续的“左括号”不能出现在一个数组里；
     * 里面有的人为了提高难度使用了栈，其实作用一样，都是计数；
     * @author: zhenghm
     * @time: 2022/12/19
     */
    private void ti7(){
        System.out.println(Arrays.toString(maxDepthAfterSplit("(()())")));
        System.out.println(Arrays.toString(maxDepthAfterSplit("()(())(())()(()(((()))))")));
    }
    private int[] maxDepthAfterSplit(String seq) {
        //首先排除空串
        if(seq.trim().length() == 0){
            return null;
        }
        /*
         * 用其所在的深度来判断是否连在一起，如果连在一起，必然深度不一样；所以，深度为偶数的，放在B组，深度为奇数的，放在A组；
         * 对于左括号，可以上述处理
         * 对于右括号，为了保证在A/B中不会出现)(，这种情况，所以都是一个(，对应一个)。于是直接可将其放置为深度为上一(深度的组里；然后深度-1；
         */
        char[] chars = seq.toCharArray();
        int[] res = new int[chars.length];
        int dep = 0;
        for(int i=0;i<chars.length;i++){
            if(chars[i]=='('){
                ++dep;
                res[i] = dep%2;
            }else{
                res[i] = dep%2;
                dep--;
            }
        }
        return res;
    }

    /**
     * @description: 有个马戏团正在设计叠罗汉的表演节目，一个人要站在另一人的肩膀上。出于实际和美观的考虑，在上面的人要比下面的人矮一点且轻一点。
     * 已知马戏团每个人的身高和体重，请编写代码计算叠罗汉最多能叠几个人。
     *
     * 我的做法不对，本来想利用map直接处理
     * 解题思路：找最大子序列；
     *
     * @author: zhenghm
     * @time: 2022/12/22
     */
    private void ti8(){
//        System.out.println(bestSeqAtIndex(new int[]{65,70,56,75,60,68},new int[]{100,150,90,190,95,11}));
        System.out.println(bestSeqAtIndex(new int[]{2868,5485,1356,1306,6017,8941,7535,4941,6331,6181},new int[]{5042,3995,7985,1651,5991,7036,9391,428,7561,8594}));


    }

    /*
     * 错误做法示例，正确做法没看明白，明天看
     */
    public int bestSeqAtIndex(int[] height, int[] weight) {
        Map<Integer,Integer> maps = new HashMap<>();
        List<Integer> lists = new ArrayList<>();
        for(int i=0;i<height.length;i++){
            maps.put(height[i],weight[i]);
            lists.add(height[i]);
        }
        Collections.sort(lists);
        int temp = 0,count =0;
        for(Integer h : lists){
            if(temp<=h && maps.getOrDefault(temp,0)<=maps.get(h)){
                count++;
                temp = h;
            }
        }
        return count;
    }
}
