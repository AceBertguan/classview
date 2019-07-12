# 一个java class文件逆向小工具
研发记录：

**时间：2019-07-10**

进度：文件解析开发中  

**时间：2019-07-11-23:00 -  2019-07-11-01:00**

进度：常量池能够解析了，目前还需要注意特殊处理的地方就是CONSTANT_Utf8_info。

原因：

| CONSTANT_Utf8_info | utf-8缩略编码字符串 | tag                           | u1   | 值为1 |
| ------------------ | ------------------- | ----------------------------- | ---- | ----- |
| length             | u2                  | utf-8缩略编码字符串占用字节数 |      |       |
| bytes              | u1                  | 长度为length的utf-8           |      |       |

因为此类u2部分是用来标识所占用的字符数量所以需要重新截取和处理字符串。

基本解析已经实现:

```java
private ConstantMemberInfo getConstantInfoIndex(int tag) {
    ConstantMemberInfo constantMemberInfo = new ConstantMemberInfo();
    switch (tag) {
        case TagInfo.CONSTANT_UTF8:
            constantMemberInfo.setConstantName("CONSTANT_Utf8_info");
            constantMemberInfo.setConstantSize(3);
            constantMemberInfo.setConstantType("u2;u1;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_INTEGER:
            constantMemberInfo.setConstantName("CONSTANT_Integer_info");
            constantMemberInfo.setConstantSize(4);
            constantMemberInfo.setConstantType("u4;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_FLOAT:
            constantMemberInfo.setConstantName("CONSTANT_Float_info");
            constantMemberInfo.setConstantSize(4);
            constantMemberInfo.setConstantType("u4;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_LONG:
            constantMemberInfo.setConstantName("CONSTANT_Long_info");
            constantMemberInfo.setConstantSize(8);
            constantMemberInfo.setConstantType("u8;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_DOUBLE:
            constantMemberInfo.setConstantName("CONSTANT_Double_info");
            constantMemberInfo.setConstantSize(8);
            constantMemberInfo.setConstantType("u8;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_CLASS:
            constantMemberInfo.setConstantName("CONSTANT_Class_info");
            constantMemberInfo.setConstantSize(2);
            constantMemberInfo.setConstantType("u2;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_STRING:
            constantMemberInfo.setConstantName("CONSTANT_String_info");
            constantMemberInfo.setConstantSize(2);
            constantMemberInfo.setConstantType("u2;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_FIELD_REF:
            constantMemberInfo.setConstantName("CONSTANT_Fieldref_info");
            constantMemberInfo.setConstantSize(4);
            constantMemberInfo.setConstantType("u2;u2;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_METHOD_REF:
            constantMemberInfo.setConstantName("CONSTANT_Methodref_info");
            constantMemberInfo.setConstantSize(4);
            constantMemberInfo.setConstantType("u2;u2;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_INTERFACE_METHOD_REF:
            constantMemberInfo.setConstantName("CONSTANT_InterfaceMethodref_info");
            constantMemberInfo.setConstantSize(4);
            constantMemberInfo.setConstantType("u2;u2;");
            return constantMemberInfo;
        case TagInfo.CONSTANT_NAME_AND_TYPE:
            constantMemberInfo.setConstantName("CONSTANT_NameAndType_info");
            constantMemberInfo.setConstantSize(4);
            constantMemberInfo.setConstantType("u2;u2;");
            return constantMemberInfo;
        default:
            System.out.println("常量池读取完毕！");
    }
    return constantMemberInfo;
```

**时间：2019-07-12-12:14 -  2019-07-12-13:24**

开发进度：完成常量池解析功能（代码乱，待封装），读取accessflag完成。（待计算转化）

```java
public class AccFlag {
    /**
     * 是否为public类型
     */
    public static final String ACC_PUBLIC = "0x0001";
    /**
     * 是否被声明为final，只有类可设置
     */
    public static final String ACC_FINAL = "0x0010";
    /**
     * 是否允许使用invokespecial字节码指令，JDK1.2以后编译出来的类这个标志为真
     */
    public static final String ACC_SUPER = "0x0020";
    /**
     * 标识这是一个接口
     */
    public static final String ACC_INTERFACE = "0x0200";
    /**
     * 是否为abstract类型，对于接口和抽象类，此标志为真，其它类为假
     */
    public static final String ACC_ABSTRACT = "0x0400";
    /**
     * 标识别这个类并非由用户代码产生
     */
    public static final String ACC_SYNTHETIC = "0x1000";
    /**
     * 标识这是一个注解
     */
    public static final String ACC_ANNOTATION = "0x2000";
    /**
     * 标识这是一个枚举
     */
    public static final String ACC_ENUM = "0x4000";

}
```

