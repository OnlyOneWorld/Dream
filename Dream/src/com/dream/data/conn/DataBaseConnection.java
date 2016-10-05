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
 * Java JDBC���ݿ�����
 * @author WYL
 *
 */
public class DataBaseConnection {

	/**
	 * ���ݿ���������(�����ȵ������ݿ�����JAR��)
	 */
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * ���ݿ�����URL
	 */
	private static final String URL = "jdbc:mysql://localhost:3306/dream";
	
	/**
	 * ���ݿ��¼�û���
	 */
	private static final String USERNAME = "root";
	
	/**
	 * ���ݿ���������
	 */
	private static final String PASSWORD = "910214";
	
	/**
	 * �������ݿ����Ӷ���
	 */
	private Connection connection = null;
	
	/**
	 * ����PreparedStatement����
	 */
	private PreparedStatement preparedStatement = null;
	
	/**
	 * ����CallableStatement����
	 */
	private CallableStatement callableStatement = null;
	
	/**
	 * �������������
	 */
	private ResultSet resultSet = null;
	
	/**
	 * ��дһ����̬��������������ݿ�����
	 */
	static{
		try{
			//�������ݿ�����
			Class.forName(DRIVER);
		}catch(ClassNotFoundException ex){
			System.out.println("ʱ��: "+System.currentTimeMillis()+"���ݿ���������ʧ��: "+ex.getMessage());
		}
	}
	
	/**
	 * @return �������ݿ�����
	 */
	public Connection getConnection(){
		try{
			//�������ݿ�����
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}catch(SQLException ex){
			System.out.println("ʱ��: "+System.currentTimeMillis()+" ���ݿ�����ʧ��...");
		}
		
		return connection;
	}
	
	/**
	 * insert update delete SQL����ִ�е�ͳһ���� 
	 * @param  sql ��ִ�е�ʧȥ����
	 * @param  params �������飬����û�в�����Ϊnull
	 * @return ����Ӱ�������
	 */
	public int executeUpdate(String sql, Object[] params){
		//ִ��SQL��֮��Ӱ�������
		int affectLine = 0;
		
		try{
			//������ݿ�����
			connection = this.getConnection();
			
			//����ִ��SQL
			preparedStatement = connection.prepareStatement(sql);
			
			//������ֵ
			if(params != null){
				for (int i = 0; i < params.length; i++) {
					preparedStatement.setObject(i+1, params[i]);
				}
			}
			//ִ��
			affectLine = preparedStatement.executeUpdate();
		}catch(SQLException ex){
			System.out.println("SQL��: "+sql+"ִ��ʧ��,�쳣��Ϣ: "+ex.getMessage());
		}finally{
			this.closeAll();
		}
		
		//����ִ�н��
		return affectLine;
	}
	
	/**
	 * SQL��ѯ,����ѯ�����Ľ��ֱ�ӷ���ResuleSet��
	 * @param sql	��ִ�е�sql��
	 * @param params�����б�
	 * @return ��ѯ�����
	 */
	public ResultSet executeQueryRS(String sql, Object[] params){
		try{
			//������ݿ�����
			connection = this.getConnection();
			
			//����SQL
			preparedStatement = connection.prepareStatement(sql);
			
			//������ֵ
			if(params != null){
				for (int i = 0; i < params.length; i++) {
					preparedStatement.setObject(i+1, params[i]);
				}
			}
			//ִ��֮�󷵻ؽ��
			resultSet = preparedStatement.executeQuery();
		}catch(SQLException ex){
			System.out.println("SQL��: "+sql+" ִ��ʧ��: "+ex.getMessage());
		}
		
		//����ִ�к�Ľ����
		return resultSet;
	}
	
	/**
	 * SQL ��ѯ����ѯ�����һ��һ�� 
	 * @param sql	��ִ�е�SQL���
	 * @param params �����б�
	 * @return 
	 */
	public Object executeQuerySingle(String sql, Object[] params){
		Object object = null;
		
		try{
			//������ݿ�����
			connection = this.getConnection();
			
			//����sql
			preparedStatement = connection.prepareStatement(sql);
			
			//������ֵ
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
			System.out.println("SQL��: "+sql+" ִ��ʧ��: "+ex.getMessage());
		}finally{
			this.closeAll();
		}
		
		//���ؽ��
		return object;
	}
	
	public List<Object> executeQuery(String sql, Object[] params){
		
		//ִ��sql�Ĳ��һ�ý����
		ResultSet rs = this.executeQueryRS(sql, params);
		
		//����ResultSetMetaData����
		ResultSetMetaData rsmd = null;
		
		// ���������  
        int columnCount = 0;  
        try {  
            rsmd = rs.getMetaData();  
              
            // ��ý��������  
            columnCount = rsmd.getColumnCount();  
        } catch (SQLException e1) {  
            System.out.println("ִ��ʧ��: "+e1.getMessage());  
        }  
  
        // ����List  
        List<Object> list = new ArrayList<Object>();  
  
        try {  
            // ��ResultSet�Ľ�����浽List��  
            while(rs.next()) {  
                Map<String, Object> map = new HashMap<String, Object>();  
                for (int i = 1; i <= columnCount; i++) {  
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));  
                }  
                list.add(map);  
            }  
        } catch (SQLException e) {  
            System.out.println("ִ��ʧ��: "+e.getMessage());  
        } finally {  
            // �ر�������Դ  
            closeAll();  
        }  
  
        return list;  
    } 
	
	/** 
     * �洢���̴���һ����������ķ��� 
     * @param sql �洢������� 
     * @param params �������� 
     * @param outParamPos �������λ�� 
     * @param SqlType ����������� 
     * @return ���������ֵ 
     */  
    public Object excuteQuery(String sql, Object[] params,int outParamPos, int SqlType) {  
        Object object = null;  
        connection = this.getConnection();  
        try {  
            // ���ô洢����  
            callableStatement = connection.prepareCall(sql);  
              
            // ��������ֵ  
            if(params != null) {  
                for(int i = 0; i < params.length; i++) {  
                    callableStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // ע���������  
            callableStatement.registerOutParameter(outParamPos, SqlType);  
              
            // ִ��  
            callableStatement.execute();  
              
            // �õ��������  
            object = callableStatement.getObject(outParamPos);  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            // �ͷ���Դ  
            closeAll();  
        }   
        return object;  
    }  
    
    
	/**
	 * �رպ����ݿ���ص���������
	 */
	public void closeAll(){
		//�رս����
		if(resultSet != null){
			try{
				resultSet.close();
			}catch(SQLException ex){
				System.out.println("������ر��쳣: "+ex.getMessage());
			}
		}
		
		//�ر�preparedStatement
		if(preparedStatement != null){
			try{
				preparedStatement.close();
			}catch(SQLException ex){
				System.out.println(preparedStatement+" �ر��쳣:"+ ex.getMessage());
			}
		}
		
		//�ر�callableStatement
		if(callableStatement != null){
			try{
				callableStatement.close();
			}catch(SQLException ex){
				System.out.println("callableStatement�ر��쳣: "+ex.getMessage());
			}
		}
		
		//�ر����ݿ�����connection
		if(connection != null){
			try{
				connection.close();
			}catch(SQLException ex){
				System.out.println("���ݿ�����connection�ر��쳣: "+ex.getMessage());
			}
		}
	}
}