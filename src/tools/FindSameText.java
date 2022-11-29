package tools;

import java.util.Arrays;
import java.util.HashMap;

public class FindSameText {
    public static void main(String[] args) {
        String txt = "《非诚勿扰》\n" +
                "《大鱼海棠》\n" +
                "《巴黎最后的探戈》\n" +
                "《姐弟恋》\n" +
                "《假结婚》\n" +
                "《重塑人生》\n" +
                "《春心荡漾》\n" +
                "《高材生》\n" +
                "《诸位》\n" +
                "《赎罪》\n" +
                "《一吻定情》\n" +
                "《我要我们在一起》\n" +
                "《你的婚礼》\n" +
                "《余波》\n" +
                "《完美约会》\n" +
                "《爱在黎明破晓前》\n" +
                "《怦然心动》\n" +
                "《侧耳倾听》\n" +
                "《遇见你之前》\n" +
                "《罗马假日》\n" +
                "《泰坦尼克号》\n" +
                "《恋恋笔记本》\n" +
                "《初恋这点小事》\n" +
                "《吋空恋旅人》\n" +
                "《你的名字》\n" +
                "《情书》：日本神户某个飘雪的冬日，渡边博子\n" +
                "《真爱至上》\n" +
                "《美丽心灵》\n" +
                "《和summer的500天》\n" +
                "《卡罗尔》\n" +
                "《迷失东京》\n" +
                "《爱你，罗茜》\n" +
                "《公主日记》\n" +
                "《傲慢与偏见》\n" +
                "《初吻》\n" +
                "《这个杀手不太冷》\n" +
                "《爱乐之城》\n" +
                "《暮光之城》\n" +
                "《天使爱美丽》\n" +
                "《他其实没那么喜欢你》\n" +
                "《简爱》\n" +
                "《小妇人》\n" +
                "《大鱼》\n" +
                "《卡萨布兰卡》\n" +
                "《魂断蓝桥》\n" +
                "《乱世佳人》\n" +
                "《七年之痒》\n" +
                "《沉静如海》\n" +
                "《何以笙箫默》\n" +
                "《何以笙箫默》\n" +
                "《最好的我们》";
        /*
         * @description: 测试使用
         */ 
        String ps = "《111》\n《222》\n《333》";
        /*
         * @description: 正则表达式中的“双反斜线加其他字符”匹配“一个斜线加其他字符”，四个反斜线匹配两个反斜线字符，
         * 没有匹配单个子一个反斜线的方式，因为本身字符串中不允许出现；
         */
        String[] ss = txt.split("\\n");
        int resize = (int) Arrays.stream(ss).distinct().count();
        HashMap<String, Integer> hashMap = new HashMap<>(resize);
        if(ss.length!= resize){
            System.out.println("有重复标题");
            for(String sg : ss){
                hashMap.put(sg,hashMap.getOrDefault(sg,0)+1);
            }
            for(String hg : hashMap.keySet()){
                if(hashMap.get(hg)>1){
                    System.out.println("重复标题："+hg);
                }
            }
        }else{
            System.out.println("没有有重复标题");
        }
        
    }
}
