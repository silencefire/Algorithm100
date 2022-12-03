package demos;

import java.util.*;

/**
 * @description: 字符串操作
 * 问题注意点：
 * 1.边界值，需要自测
 * 2.注意输入的循环用for(sc.hasNext())
 *      a.forEach循环不能修改循环数据的值，需要找另一个容器，或者使用for循环；
 *
 * 3.字符串排序工具：
 *      a.Collections.sort排序，使用 List，可以获取最大&最小值
 *      b.Arrays.sort排序，使用 数组
 *      c.流排序，Arrays.stream.sorted.foreach(System.out::println);
 *      d.字符工具，可以判断大小写和转换字符；
 * 4.字符数组转字符串：String.valueOf();
 * 5.集合的使用：
 *      Collection：可使用sort，放入自己的比较器,实现特殊排序，但是只能对于非空数据进行冒泡排序，中间有空格的慎用；
 *
 */
public class DemoA1 {
    public static void main(String[] args) {
        DemoA1  a = new DemoA1();
        a.compStrs();

    }
    /**
     * @description:
     * 1.字符串最后一个单词的长度
     * 输入一行，代表要计算的字符串，非空，长度小于5000
     * 输出一个整数，表示输入字符串最后一个单词的长度
     */
    private void ti1(){
        Scanner s = new Scanner(System.in);
        String str = s.nextLine();
        String[] strs = str.split(" ");
        System.out.println(strs[strs.length-1].length());
    }
    /** 
     * @description: 输入一个字符串，请按长度为8拆分每个输入字符串并进行输出,长度不是8整数倍的字符串请在后面补数字0，空字符串不处理
     * 连续输入字符串(每个字符串长度小于等于100)
     * 依次输出所有分割后的长度为8的新字符串
     */ 
    private void ti2(){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        if(s.length()==0){
            System.out.println();
        }
        int o = 8-s.length()%8;
        while(s.length()>=8){
            System.out.println(s.substring(0,8));
            s = s.substring(8);
        }
        StringBuilder sb = new StringBuilder(s);
        /*注意边界值问题，自己测试的时候需要注意*/
        while(sb.length()!=8 && sb.length()!=0){
            sb.append("0");
        }
        System.out.println(sb);
    }
    /** 
     * @description: 接受一个只包含小写字母的字符串，然后输出该字符串反转后的字符串。（字符串长度不超过1000）
     * 输入一行，为一个只包含小写字母的字符串。
     * 输出该字符串反转后的字符串。
     *
     * 主要使用StringBuilder的工具方法reverse
     */
    private void ti3(){
        Scanner sc = new Scanner(System.in);
        String s;
        while(sc.hasNext()){
            s = sc.nextLine();
            System.out.println(new StringBuilder(s).reverse());
        }
    }
    /** 
     * @description: 字符串排序
     * 输入第一行为一个正整数n（1<=n<=1000），下面n行为n个字符串(字符串长度<=100),字符串中只含有大小写字母。
     * 数据输出n行，输出结果为按照字典序排列的字符串。
     *
     * 使用集合工具类的排序，Collections.sort();
     */
    private void ti4(){
        Scanner sc = new Scanner(System.in);
        int rows = Integer.parseInt(sc.nextLine());
        List<String> ls = new ArrayList<>();
        for(int i = 1;i<=rows;i++){
            String s = sc.nextLine();
            ls.add(s);
        }
        Collections.sort(ls);
        ls.forEach(System.out::println);
    }
    /** 
     * @description: 实现删除字符串中出现次数最少的字符，若出现次数最少的字符有多个，则把出现次数最少的字符都删除。
     * 输出删除这些单词后的字符串，字符串中其它字符保持原来的顺序。
     */
    private void ti5(){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] chars = s.toCharArray();
        HashMap<Character, Integer> hm = new HashMap<>();
        for(Character a : chars){
            hm.put(a,hm.getOrDefault(a,0)+1);
        }
        Collection<Integer> values = hm.values();
        Integer min = Collections.min(values);

        for(Character c : hm.keySet()){
            if(Objects.equals(hm.get(c), min)){
                s= s.replaceAll(c.toString(),"");
            }
        }
        System.out.println(s);
    }
    /** 
     * @description: 将输入字符串中的字符按如下规则排序
     * 英文字母从 A 到 Z 排列，不区分大小写
     * 同一个英文字母的大小写同时存在时，按照输入顺序排列
     * 非英文字母的其它字符保持原来的位置
     * B Famous about
     * 问题：comparator内部使用冒泡算法，不能随意定义规则（尤其是有空格的字符串排序），注意该问题；
     */
    private void ti6(){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] cs = s.toCharArray();
        List<Character> lc = new ArrayList<>(cs.length);
        for(Character c : cs){
            if(Character.isLetter(c)){
                lc.add(c);
            }
        }
        Collections.sort(lc, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return Character.toLowerCase(o1)- Character.toLowerCase(o2);
            }
        });
        List<Character> nc = new ArrayList<>();
        int i=0;
        for(Character c : cs){
            if(Character.isLetter(c)){
                nc.add(lc.get(i++));
            }else{
                nc.add(c);
            }
        }
        nc.forEach(System.out::print);
    }
    /** 
     * @description: 对输入的字符串进行加解密，并输出
     * 当内容是英文字母时则用该英文字母的后一个字母替换，同时字母变换大小写,如字母a时则替换为B；字母Z时则替换为a；
     * 当内容是数字时则把该数字加1，如0替换1，1替换2，9替换0；
     * 其他字符不做变化。
     * 解密方法为加密的逆过程。
     */
    private void ti7(){
        String s = "AZaz0990";
        System.out.println(lockWord(s));
        System.out.println(unlockWord(s));
    }
    private String lockWord(String s){
        char[] cs = s.toCharArray();
        for(int i=0;i<cs.length;i++){
            if(Character.isLetter(cs[i])){
                if(cs[i] =='Z'){
                    cs[i] = 'a';
                }else if(cs[i]>='A' && cs[i]<'Z'){
                    cs[i] = Character.toLowerCase(cs[i]);
                    cs[i]++;
                }else if(cs[i] == 'z'){
                    cs[i] = 'A';
                }else if(cs[i]>='a' && cs[i]<'z'){
                    cs[i] = Character.toUpperCase(cs[i]);
                    cs[i]++;
                }
            }else if(Character.isDigit(cs[i])){
                if(cs[i]=='9'){
                    cs[i]='0';
                }else{
                    cs[i]++;
                }
            }
        }
        return String.valueOf(cs);
    }
    private String unlockWord(String s){
        char[] cs = s.toCharArray();
        for(int i=0;i<cs.length;i++){
            if(Character.isLetter(cs[i])){
                if(cs[i] =='a'){
                    cs[i] = 'Z';
                }else if(cs[i]>'a' && cs[i]<='z'){
                    cs[i] = Character.toUpperCase(cs[i]);
                    cs[i]--;
                }else if(cs[i] == 'A'){
                    cs[i] = 'z';
                }else if(cs[i]>'A' && cs[i]<='Z'){
                    cs[i] = Character.toLowerCase(cs[i]);
                    cs[i]--;
                }
            }else if(Character.isDigit(cs[i])){
                if(cs[i]=='0'){
                    cs[i]='9';
                }else{
                    cs[i]--;
                }
            }
        }
        return String.valueOf(cs);
    }
    
    /**
     * @description: 表达式求值
     * 给定一个字符串描述的算术表达式，计算出结果值。
     * 输入字符串长度不超过 100 ，合法的字符包括 ”+, -, *, /, (, )” ， ”0-9” 。
     * 8/(1+2-9)*(4*3)
     *
     * 解题思路：
     * 1.先将所有的{}和[]转换为()方便后面的统一计算
     * 2.分为三种情况：
     *  2.1是数字：算出连续的数字完整的值之后，暂存
     *  2.2是运算符：第一次将第一个数字进行存储，后面每次获取最新的操作符，结合读取的数值进行相应操作；
     *  2.3是括号：提取出式子中完整的最外层子表达式，递归求解，注意下标移到对应的）之后；
     */
    private void ti8(){
//        String s = "1*2+(4*(6/3))+3-4*(6/6)";
        String s = "1*2+(4*(6/3))";
        s = getCommonStr(s);
        Integer res = calculate(s);
        System.out.println(res);
    }
    private String getCommonStr(String s){
        s = s.replace('{','(');
        s = s.replace('[','(');
        s = s.replace('}','(');
        s = s.replace(']','(');
        return s;
    }

    private Integer calculate(String s){
        char[] chars  = s.toCharArray();
        int num = 0;
        char sign = '+';
        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<chars.length;i++){
            char charN = chars[i];
            if(Character.isDigit(charN)){
                //处理数字为具体的正常值
                num = 10 * num + (charN-'0');
            }
            //递归获取值--将最外一层的括号表达式提取出来；
            if('(' == charN){
                int count = 1;
                int end = i;
                while(count != 0){
                    end = end+1;
                    if(chars[end] == '('){
                        count++;
                    }else if(chars[end] == ')'){
                        count--;
                    }
                }
                num = calculate(s.substring(i+1,end));
                //将i重新定位到最外层表达式之后的下表
                i = end-1;
            }
            if(!Character.isDigit(charN) || i==chars.length-1){
                if('+' == sign){
                    stack.push(num);
                }else if('-' == sign){
                    stack.push(-1*num);
                }else if('*' == sign){
                    stack.push(stack.pop()*num);
                }else if('/' == sign){
                    stack.push(stack.pop()/num);
                }
                sign = charN;
                num = 0;
            }
        }
        int res = 0;
        for(Integer n : stack){
            res = res+n;
        }
        return res;
    }
   /**
    * @description:  将一个字符串str的内容颠倒过来，并输出。
    * @author: zhenghm
    * @time: 2022/11/30
    */
    private void ti9(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        StringBuilder sb = new StringBuilder(str);
        System.out.println(sb.reverse());
    }
    /**
     * @description:  验证尼科彻斯定理，即：任何一个整数m的立方都可以写成m个连续奇数之和
     * 原理就是等差序列：S(n) = n*a + n(n-1)d/2，已知S(n)是m的立方，n是项数即m，d是公差，是2，换算下来，就是
     * 首项a = m^3/n-(n-1)，然后项数是n，每次+2,就能打印出所有数字；
     * @author: zhenghm
     * @time: 2022/11/30
     */
    private void ti10(){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int sum = (int)Math.pow(num,3);
        int a = sum/num - (num-1);
        StringBuilder sb = new StringBuilder();
        for(int i=1;i<=num;i++){
            sb.append(a);
            if(i == num){
                sb.append("=").append(sum);
            }else{
                sb.append("+");
            }
            a = a+2;
        }
        System.out.println(sb);
    }
    /**
     * @description:  给定两个只包含小写字母的字符串，计算两个字符串的最大公共子串的长度。
     * 解题思路：找到最短的字符串，前后游标相互移动，用string.contains方法判断是否为字串，再判断字串的长度；
     * @author: zhenghm
     * @time: 2022/12/1
     */
    private void ti11(){
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();
        String s1 = str1.length()>str2.length()?str2:str1;//短的串
        String s2 = str1.length()>str2.length()?str1:str2;//长的串
        int n = 0;//最大长度
        for(int i=0;i<s1.length();i++){
            for(int j=s1.length();j>i;j--){
                if(s2.contains(s1.substring(i,j))){
                    n = Math.max(n, s1.substring(i, j).length());
                }
            }
        }
        System.out.println(n);
    }
    /**
     * @description: 求一个int类型数字对应的二进制数字中1的最大连续数，例如3的二进制为00000011，最大连续2个1
     * 难点：Integer的toBinaryString的方法
     * @author: zhenghm
     * @time: 2022/12/1
     */
    private void ti12(){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        String bStr = Integer.toBinaryString(num);
        String[] bArrays = bStr.split("0");
        int max = 0;
        for(String s : bArrays){
            max = Math.max(max,s.length());
        }
        System.out.println(max);
    }

    /**
     * @description: 给定一个仅包含小写字母的字符串，求它的最长回文子串的长度。
     * 所谓回文串，指左右对称的字符串。
     * 所谓子串，指一个字符串删掉其部分前缀和后缀（也可以不删）的字符串
     * 关键：思想：从字符串两边放置游标，循环验证；
     * 验证回文的方法：用reverse就能轻易验证
     * @author: zhenghm
     * @time: 2022/12/1
     */
    private void ti13(){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int max = 0;
        for(int i=0;i<s.length();i++){
            for(int j=s.length();j>i;j--){
                if(judgeOAOStr(s.substring(i,j))){
                    max = Math.max(max,s.substring(i,j).length());
                }
            }
        }
        System.out.println(max);
    }
    private boolean judgeOAOStr(String s){
        return s.equals(new StringBuilder(s).reverse().toString());
    }

    /**
     * @description: 等差数列 2，5，8，11，14。。。。（从 2 开始的 3 为公差的等差数列）
     * 输出求等差数列前n项和
     * 可以使用等差数列公式，但是感觉没必要；
     * 等差公式还是放上来：A(n)=a1 + (n-1)d
     * @author: zhenghm
     * @time: 2022/12/1
     */
    private void ti14(){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int a = 2;
        int sum = 0;
        for(int i=1;i<=num;i++){
            sum = sum+a;
            a = a+3;
        }
        System.out.println(sum);
    }
    /**
     * @description:  密码按如下规则进行计分，并根据不同的得分为密码进行安全等级划分。
     * switch在13之前是没办法使用的，所以用for来进行判断
     * @author: zhenghm
     * @time: 2022/12/2
     * 例子：38$@NoNoN
     * 问题：判断字符串的大小写，字母，数字，都可以用Character里的方法；
     */
    private void ti15(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int num = 0;

        /*判断长度分数*/
        int strLength = str.length();
        if(strLength>=8){
            num += 25;
        }else if(strLength>=5){
            num += 10;
        }else{
            num += 5;
        }
        /*
        * 判断字母分数
        * 判断数字分数
        * 判断符号分数
        */
        char[] chars = str.toCharArray();
        int letterNum = 0;//字母个数
        int digitNum = 0;//数字个数
        boolean digitAndLetter = false;
        for(char c : chars){
            if(Character.isLetter(c)){
                letterNum++;
            }
            if(Character.isDigit(c)){
                digitNum++;
            }
        }
        if(letterNum>0){
            if(str.toLowerCase().equals(str)){
                num += 10;
            }else{
                num += 20;
                digitAndLetter = true;
            }
        }
        if(digitNum>1){
            num += 20;
        }else if(digitNum==1){
            num += 10;
        }
        int symbolNum = str.length()-digitNum-letterNum;//符号个数
        if(symbolNum>1){
            num += 25;
        }else if(symbolNum==1){
            num += 10;
        }

        /*判断奖励分数*/
        if(digitAndLetter && digitNum>0 && symbolNum>0){
            num += 5;
        }else if(letterNum>0 && digitNum>0 && symbolNum>0){
            num += 3;
        }else if(letterNum>0 && digitNum>0){
            num +=2;
        }
        System.out.println(num);
        System.out.println(getLevel(num));
    }
    private String getLevel(int num){
        if(num>=90){
            return "VERY_SECURE";
        }else if(num>=80){
            return "SECURE";
        }else if(num>=70){
            return "VERY_STRONG";
        }else if(num>=60){
            return "STRONG";
        }else if(num>=50){
            return "AVERAGE";
        }else if(num>=25){
            return "WEAK";
        }else{
            return "VERY_WEAK";
        }
    }

    /**
     * @description:  计算字符串中含有的不同字符的个数
     * @author: zhenghm
     * @time: 2022/12/2
     * 直接用set的元素不重复特性处理就可以；
     */
    private void ti16(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        char[] chars = str.toCharArray();
        Set<Character> sets = new HashSet<>();
        for(char c : chars){
            sets.add(c);
        }
        System.out.println(sets.size());
    }

    /**
     * @description:  输入一个字符串和一个整数 k ，截取字符串的前k个字符并输出
     * @author: zhenghm
     * @time: 2022/12/3
     */
    private void ti17(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int num = sc.nextInt();
        System.out.println(str.substring(0,num));
    }

    /**
     * @description: 任意一个偶数（大于2）都可以由2个素数组成，组成偶数的2个素数有很多种情况，
     * 本题目要求输出组成指定偶数的两个素数差值最小的素数对
     * @author: zhenghm
     * @time: 2022/12/3
     *
     * 分成两步：第一步判断素数，第二部穷举
     * 问题：素数这里的判断卡住了，用取余%就能判断，但是循环的时候要比较的不是i<num，而是用i<=Math.sqrt(num)
     * Math的abs绝对值的使用
     */
    private void ti18(){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int res = Integer.MAX_VALUE;
        int[] values = new int[2];
        for(int i=1;i<num;i=i+2){
            if(isPrime(i) && isPrime(num-i)){
                if(Math.abs(num-2*i)<res){
                    res = Math.abs(num-2*i);
                    values[0] = Math.min(i,num-i);
                    values[1] = num-values[0];
                }
            }
        }
        System.out.println(values[0]);
        System.out.println(values[1]);
    }

    /**
     * 判断是否素数
     * 注意这里比较的上限是num的平方根；
     * 另外，不是i<Math.sqrt(num)，其中=的情况忘记算进去了；
     */
    private boolean isPrime(int num){
        for(int i=2;i<=Math.sqrt(num);i++){
            if(num%i == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * @description: 输入一行字符，分别统计出包含英文字母、空格、数字和其它字符的个数。
     * @author: zhenghm
     * @time: 2022/12/3
     * 另外的做法：使用正则表达式
     * 字母正则：([A-Z]+)|([a-z]+)
     * 数字正则：[0-9]+
     */
    private void ti19(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        char[] chars = str.toCharArray();
        int charNum = 0;//字母
        int noNum = 0;//数字
        int symbolNum = 0;//其他符号
        for(char c : chars){
            if(Character.isDigit(c)){
                noNum++;
            }else if(Character.isLetter(c)){
                charNum++;
            }else{
                symbolNum++;
            }
        }
        int nullNum = str.length()  - str.replaceAll(" ","").length();
        System.out.println(charNum);
        System.out.println(nullNum);
        System.out.println(noNum);
        System.out.println(symbolNum-nullNum);
    }

    /**
     * @description: 给定 n 个字符串，请对 n 个字符串按照字典序排列。
     * @author: zhenghm
     * @time: 2022/12/3
     */
    private void ti20(){
        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        String[] strs = new String[num];
        for(int i=0;i<num;i++){
            strs[i] = sc.nextLine();
        }
        Arrays.sort(strs);
        Arrays.stream(strs).forEach(System.out::println);
    }

    //如果自己实现比较器，这种做法可以推荐
    //这里注意：从小到大，正序：o1-o2，从大到小，逆序：o2-o1
    private void compStrs(){
        List<String> strss = new ArrayList<>();
        strss.add("fff");
        strss.add("aaa");
        strss.add("ccc");
        strss.add("bbb");
        strss.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                for(int i=0;i<Math.min(o1.length(),o2.length());){
                    if(o1.charAt(i)<o2.charAt(i)){
                        return -1;
                    }else{
                        i++;
                    }
                }
                return 0;
            }
        });
        strss.forEach(System.out::println);
    }
}
