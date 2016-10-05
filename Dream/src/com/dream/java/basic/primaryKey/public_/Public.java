package com.dream.java.basic.primaryKey.public_;

/**
 * public:这是java中的访问修饰符，或者说是java中权限最高的访问修饰符。被public修饰的变
 * 量或者常量在Java中任何地方都可以访问的。
 * @author Copy Jobs
 *
 */
public class Public {

	//在这里测试使用public什么几个变量和常量：
	public String username;
	
	public int length;
	
	public Double weight;
	
	public boolean flag;
	
	//java中的八种基本数据类型
	//一、四中整数类型:(byte、short、int、long)：
	//1、byte：8 位，用于表示最小数据单位，如文件中数据，-128~127  
	//2、short：16 位，很少用，-32768 ~ 32767 
	//3、int：32 位、最常用，-2^31-1~2^31  （21 亿）
	//4、long：64 位、次常用 
	// 注意事项：    int i=5; // 5 叫直接量（或字面量），即 直接写出的常数。    整数字面量默认
	//都为 int 类型，所以在定义的 long 型数据后面加 L或 l。  
	//小于 32 位数的变量，都按 int 结果计算。    强转符比数学运算符优先级高。见常量与变量中的例子。
	public byte a = -127; //假如在这里定义了一个byte类型的常量，但是字面量大于127或者小鱼-127的话就会编译异常。
	
	public short b = 32767; //同byte
	
	public int c = 111222333;//同byte
	
	public long d = 10000000L;
	//以上就是java基本数据类型中的整型部分。需要注意的就是他们的数值越界。
	
	//两种浮点数类型(float、double)：
	//1、float：32 位，后缀 F 或 f，1 位符号位，8 位指数，23 位有效尾数。   
	//2、double：64 位，最常用，后缀 D 或 d，1 位符号位，11 位指数，52 位有效尾 
	//注意事项：    二 进 制 浮 点 数 ： 
	//1010100010=101010001.0*2=10101000.10*2^10（2次方)=1010100.010*2^11(3次方)= . 1010100010*2^1010(10次方)    尾数：  . 1010100010   指数：1010   基数：2    浮点数字面量默认都为 double 类型，所以在定义的 float 型数据后面加F 或 f；double 类型可不写后缀，但在小数计算中一定要写 D 或 X.X    float  的精度没有 long 高，有效位数（尾数）短。    float  的范围大于 long  指数可以很大。    浮点数是不精确的，不能对浮点数进行精确比较。
	public float f = 12.36f;//假如在定义float常量时不加F或者f那么就会编译错误
	
	public float F = 12.3355555552F;
	
	public double doub = 12.33333333333D;
	
	public double doub2 = 45.32152354d;
	
	
	//一种字符类型(char)：    char：16 位，是整数类型，用单引号括起来的 1 个字符（可以是一个中文字符），
	//使用 Unicode 码代表字符，0~2^16-1（65535） 。    注意事项：    不能为 0个字符。    转义字符：\n
	//换行  \r  回车  \t Tab 字符  \" 双引号  \\ 表示一个\    两字符 char 中间用“+”连接，内部" +
	//"先把字符转成 int 类型，再进行加法运算，char 本质就是个数！二进制的，显示的时候，经过“处理”显示为字符。
	char cc = 'd';//必须使用单引号括起来，而且必须是一个字符，假如多于一个字符就会编译出错。
	char c_ = '罗';//也可以是一个汉字，而且也必须用单引号括起来。
	//char ddd = "c";假如使用双引号的话就会编译错误
	

	//还有一种就是boolean(布尔类型):
	boolean bool = true;
	
	boolean flg = false;
	
	//5）类型转换：       char-->    自动转换：byte-->short-->int-->long-->float-->double                   
	//强制转换：①会损失精度，产生误差，小数点以后的数字全部舍弃。②容易超过取值范围。

	 
	//8位：Byte（字节型）          
	//16位：short（短整型）、char（字符型）          
	//32位：int（整型）、float（单精度型/浮点型）          
	//64位：long（长整型）、double（双精度型）        
	//最后一个：boolean(布尔类型
	
	/**
	 * 最后对public作如下几点总结:
	 * 
	 * 1、当public修饰一个变量的时候，这个变量可以直接被其他类获得这个类的实例之后直接调用这个变量。
	 * 2、档public修饰一个类的时候表明这个类是可以直接被其他类实例化的。
	 */
}
