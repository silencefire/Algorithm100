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
        a.ti8();
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
            System.out.println("");;
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
        System.out.println(sb.toString());
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
            System.out.println(new StringBuilder(s).reverse().toString());
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
            if(hm.get(c) == min){
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

}
