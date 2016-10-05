package com.dream.data.conn.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dream.data.conn.DataBaseConnection;

/**
 * 测试数据库操作
 * @author WYL
 *
 */
public class DataBaseConnectionTest {
	DataBaseConnection connection;

	public String getTime(){
		//获取当前日期
		Date date = new Date();
		
		//获取日期格式化形式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String formatDate = sdf.format(date);
		//返回格式化之后的日期
		return formatDate;
	}
	
	
	@Before
	public void before(){
		//获得数据库操作实例
		connection = new DataBaseConnection();
		System.out.println("程序启动,启动时间: "+this.getTime());
	}
	
	@After
	public void after(){
		System.out.println("程序终止,终止时间: "+this.getTime());
	}
	
	@Test
	public void test(){
		System.out.println("测试测试结构...");
	}
	
	/**
	 * 测试数据库连接是否正常
	 */
	@Test
	public void testDBConnection(){
		Connection conn = this.connection.getConnection();
		
		if(conn != null){
			System.out.println("数据库连接正常...");
		}else{
			System.out.println("数据库连接异常...");
		}
	}
	
	/**
	 * 测试插入数据
	 */
	@Test
	public void insert(){
		//插入数据SQL文
		String sql = " insert into test(username, password, email) values(?,?,?) ";
		
		//设置值列表
		Object[] params = {"zhangxueyiou","123456","zhangxueyiou@163.com"};
		
		int affectLine = this.connection.executeUpdate(sql, params);
		
		System.out.println("影响的行数为: "+affectLine);
	}
	/**
	 * 更新数据库数据
	 */
	@Test
	public void update(){
		//插入数据SQL文
		String sql = " update test set username = ? where id = ? ";
		
		//设置值列表
		Object[] params = {"xidada","4"};
		
		int affectLine = this.connection.executeUpdate(sql, params);
		
		System.out.println("影响的行数为: "+affectLine);
	}
	
	/**
	 * 删除数据库数据
	 */
	@Test
	public void delete(){
		//插入数据SQL文
		String sql = " delete from test where id > ? and id <= ? ";
		
		//设置值列表
		Object[] params = {"4", "5"};
		
		int affectLine = this.connection.executeUpdate(sql, params);
		
		System.out.println("影响的行数为: "+affectLine);
	}
	
	/**
	 * 将查询出来的数据放入ResultSet中
	 */
	@Test
	public void selectToResultSet(){
		//查询数据的sql文
		String sql = " select * from test where id <= ? ";
		
		//参数列表
		Object[] params = {"4"};
		
		//执行查询
		ResultSet rs = this.connection.executeQueryRS(sql, params);
		
		//将查询的数据打印输出
		try {
			while(rs.next()){
				System.out.print("编   号 :"+rs.getInt("id")+"  ");
				System.out.print("用户名:"+rs.getString("username")+"  ");
				System.out.print("密 码   :"+rs.getString("password")+"  ");
				System.out.println("邮 箱   :"+rs.getString("email")+"  ");
			}
		} catch (SQLException e) {
			System.out.println("查询出数据异常： "+e.getMessage());
		}
	}
	
	/**
	 * 查询出一行一列
	 */
	@Test
	public void selectSingle(){
		//查询数据的sql文
		String sql = " select * from test where id <= ? ";
		
		//参数列表
		Object[] params = {"4"};
		
		//执行查询
		Object object = this.connection.executeQuerySingle(sql, params);
		
		System.out.println("查询出来的数据: "+object);
	}
	
	@Test
	public void executeQueryTest(){
		//查询数据的sql文
		String sql = " select * from test where id <= ? ";
		
		//参数列表
		Object[] params = {"4"};
		
		List<Object> list = this.connection.executeQuery(sql, params);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println("编号:"+list.get(i));
		}
	}
}
