## 1.IP转数字 ，用于比大小，用在求IP段范围中

```java
//IP转换成Long型数字 [java实现]
/**
  * https://blog.csdn.net/qq_36968512/article/details/82494437 [ip地址转换成Long型数字算法和原理]
  * IP地址：(a.b.c.d) 4个“8位二进制数” a,b,c,d都是0-255之间的十进制整数
  * IP地址转换成long型数字：
  *   基础算法：Ip_Num = ip(0)*256*256*256 + ip(1)*256*256 + ip(2)*256 + ip(3)
  *   快速算法：Ip_Num =  fragments(i).toLong | ipNum << 8L [底层实现逻辑与基础算法相同]
  *       其中"|"符号的含义就是将两个数字的二进制按右端对齐，只要有数字1就换1，当两个数字都是0才是0
  *       注意：这两个位运算符的优先级，左移运算符的优先级高于按位或运算符的优先级
  * 码住：https://blog.csdn.net/qq_36968512/article/details/82494437 [ipToLong详细讲解]
  */
//基础算法
public static Long ipToLong(String ipaddr){
        String[] ip = ipaddr.split("\\.");
        Long ipLong = 256 * 256 * 256 * Long.parseLong(ip[0]) + 256 * 256 * Long.parseLong(ip[1]) + 256 * Long.parseLong(ip[2]) + Long.parseLong(ip[3]);
    
   
　　　　　　return ipLong;
    }

//进阶算法
public static Long ipToLong(String ipaddr){
        String[] ip = ipaddr.split("\\.");
        Long ipNum = 0L;
        for(int i = 0; i < ip.length;i++){

            ipNum = Long.parseLong(ip[i]) | ipNum << 8L;
//            System.out.println(ipNum);//打印每一步结果，方便理解这行代码的逻辑
             /**
             * 每一步打印结果
             * 1
             * 256
             * 65537
             * 16777472
             */
        }

        return ipNum;
    }

//Long型数字转换成IP地址
 /**
     * 整数转成ip地址.
     * @param ipLong
     * @return
     */
    public static String long2ip(long ipLong) {
        //long ipLong = 1037591503;
        long mask[] = {0x000000FF,0x0000FF00,0x00FF0000,0xFF000000};
        long num = 0;
        StringBuffer ipInfo = new StringBuffer();
        for(int i=0;i<4;i++){
            num = (ipLong & mask[i])>>(i*8);
            if(i>0) ipInfo.insert(0,".");
            ipInfo.insert(0,Long.toString(num,10));
        }
        return ipInfo.toString();
    }
```

```scala
//spark中的实现
def ip2Long(ip: String):Long ={
    //字符集合匹配，匹配集合中所包含的任意一个字符
    val fragments = ip.split("[.]")
    var ipNum = 0L

    for (i <- 0 until fragments.length){
      ipNum = fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }
```



## 2.二分法，查找某一IP段所属范围

```scala

```
