package com.dream.data.conn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java JDBC数据库连接
 * @author WYL
 *
 */
public class DataBaseConnection {

	/**
	 * 数据库连接驱动(必须先导入数据库驱动JAR包)
	 */
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * 数据库连接URL
	 */
	private static final String URL = "jdbc:mysql://localhost:3306/dream";
	
	/**
	 * 数据库登录用户名
	 */
	private static final String USERNAME = "root";
	
	/**
	 * 数据库连接密码
	 */
	private static final String PASSWORD = "910214";
	
	/**
	 * 创建数据库连接对象
	 */
	private Connection connection = null;
	
	/**
	 * 创建PreparedStatement对象
	 */
	private PreparedStatement preparedStatement = null;
	
	/**
	 * 创建CallableStatement对象
	 */
	private CallableStatement callableStatement = null;
	
	/**
	 * 创建结果集对象
	 */
	private ResultSet resultSet = null;
	
	/**
	 * 编写一个静态代码块来加载数据库驱动
	 */
	static{
		try{
			//加载数据库驱动
			Class.forName(DRIVER);
		}catch(ClassNotFoundException ex){
			System.out.println("时间: "+System.currentTimeMillis()+"数据库驱动加载失败: "+ex.getMessage());
		}
	}
	
	/**
	 * @return 返回数据库连接
	 */
	public Connection getConnection(){
		try{
			//建立数据库连接
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}catch(SQLException ex){
			System.out.println("时间: "+System.currentTimeMillis()+" 数据库连接失败...");
		}
		
		return connection;
	}
	
	/**
	 * insert update delete SQL语句的执行的统一方法 
	 * @param  sql 待执行的失去了文
	 * @param  params 参数数组，假如没有参数则为null
	 * @return 返回影响的行数
	 */
	public int executeUpdate(String sql, Object[] params){
		//执行SQL文之后影响的行数
		int affectLine = 0;
		
		try{
			//获得数据库连接
			connection = this.getConnection();
			
			//调用执行SQL
			preparedStatement = connection.prepareStatement(sql);
			
			//参数赋值
			if(params != null){
				for (int i = 0; i < params.length; i++) {
					preparedStatement.setObject(i+1, params[i]);
				}
			}
			//执行
			affectLine = preparedStatement.executeUpdate();
		}catch(SQLException ex){
			System.out.println("SQL文: "+sql+"执行失败,异常信息: "+ex.getMessage());
		}finally{
			this.closeAll();
		}
		
		//返回执行结果
		return affectLine;
	}
	
	/**
	 * SQL查询,将查询出来的结果直接放入ResuleSet中
	 * @param sql	待执行的sql文
	 * @param params参数列表
	 * @return 查询结果集
	 */
	public ResultSet executeQueryRS(String sql, Object[] params){
		try{
			//获得数据库连接
			connection = this.getConnection();
			
			//调用SQL
			preparedStatement = connection.prepareStatement(sql);
			
			//参数赋值
			if(params != null){
				for (int i = 0; i < params.length; i++) {
					preparedStatement.setObject(i+1, params[i]);
				}
			}
			//执行之后返回结果
			resultSet = preparedStatement.executeQuery();
		}catch(SQLException ex){
			System.out.println("SQL文: "+sql+" 执行失败: "+ex.getMessage());
		}
		
		//返回执行后的结果集
		return resultSet;
	}
	
	/**
	 * SQL 查询将查询结果：一行一列 
	 * @param sql	待执行的SQL语句
	 * @param params 参数列表
	 * @return 
	 */
	public Object executeQuerySingle(String sql, Object[] params){
		Object object = null;
		
		try{
			//获得数据库连接
			connection = this.getConnection();
			
			//调用sql
			preparedStatement = connection.prepareStatement(sql);
			
			//参数赋值
			if(params != null){
				for (int i = 0; i < params.length; i++) {
					preparedStatement.setObject(i+1, params[i]);
				}
			}
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				object = resultSet.getObject(1);
			}
		}catch(SQLException ex){
			System.out.println("SQL文: "+sql+" 执行失败: "+ex.getMessage());
		}finally{
			this.closeAll();
		}
		
		//返回结果
		return object;
	}
	
	public List<Object> executeQuery(String sql, Object[] params){
		
		//执行sql文并且获得结果集
		ResultSet rs = this.executeQueryRS(sql, params);
		
		//创建ResultSetMetaData对象
		ResultSetMetaData rsmd = null;
		
		// 结果集列数  
        int columnCount = 0;  
        try {  
            rsmd = rs.getMetaData();  
              
            // 获得结果集列数  
            columnCount = rsmd.getColumnCount();  
        } catch (SQLException e1) {  
            System.out.println("执行失败: "+e1.getMessage());  
        }  
  
        // 创建List  
        List<Object> list = new ArrayList<Object>();  
  
        try {  
            // 将ResultSet的结果保存到List中  
            while(rs.next()) {  
                Map<String, Object> map = new HashMap<String, Object>();  
                for (int i = 1; i <= columnCount; i++) {  
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));  
                }  
                list.add(map);  
            }  
        } catch (SQLException e) {  
            System.out.println("执行失败: "+e.getMessage());  
        } finally {  
            // 关闭所有资源  
            closeAll();  
        }  
  
        return list;  
    } 
	
	/** 
     * 存储过程带有一个输出参数的方法 
     * @param sql 存储过程语句 
     * @param params 参数数组 
     * @param outParamPos 输出参数位置 
     * @param SqlType 输出参数类型 
     * @return 输出参数的值 
     */  
    public Object excuteQuery(String sql, Object[] params,int outParamPos, int SqlType) {  
        Object object = null;  
        connection = this.getConnection();  
        try {  
            // 调用存储过程  
            callableStatement = connection.prepareCall(sql);  
              
            // 给参数赋值  
            if(params != null) {  
                for(int i = 0; i < params.length; i++) {  
                    callableStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 注册输出参数  
            callableStatement.registerOutParameter(outParamPos, SqlType);  
              
            // 执行  
            callableStatement.execute();  
              
            // 得到输出参数  
            object = callableStatement.getObject(outParamPos);  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            // 释放资源  
            closeAll();  
        }   
        return object;  
    }  
    
    
	/**
	 * 关闭和数据库相关的所有连接
	 */
	public void closeAll(){
		//关闭结果集
		if(resultSet != null){
			try{
				resultSet.close();
			}catch(SQLException ex){
				System.out.println("结果集关闭异常: "+ex.getMessage());
			}
		}
		
		//关闭preparedStatement
		if(preparedStatement != null){
			try{
				preparedStatement.close();
			}catch(SQLException ex){
				System.out.println(preparedStatement+" 关闭异常:"+ ex.getMessage());
			}
		}
		
		//关闭callableStatement
		if(callableStatement != null){
			try{
				callableStatement.close();
			}catch(SQLException ex){
				System.out.println("callableStatement关闭异常: "+ex.getMessage());
			}
		}
		
		//关闭数据库连接connection
		if(connection != null){
			try{
				connection.close();
			}catch(SQLException ex){
				System.out.println("数据库连接connection关闭异常: "+ex.getMessage());
			}
		}
	}
}