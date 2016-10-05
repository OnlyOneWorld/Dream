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
 * �������ݿ����
 * @author WYL
 *
 */
public class DataBaseConnectionTest {
	DataBaseConnection connection;

	public String getTime(){
		//��ȡ��ǰ����
		Date date = new Date();
		
		//��ȡ���ڸ�ʽ����ʽ
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String formatDate = sdf.format(date);
		//���ظ�ʽ��֮�������
		return formatDate;
	}
	
	
	@Before
	public void before(){
		//������ݿ����ʵ��
		connection = new DataBaseConnection();
		System.out.println("��������,����ʱ��: "+this.getTime());
	}
	
	@After
	public void after(){
		System.out.println("������ֹ,��ֹʱ��: "+this.getTime());
	}
	
	@Test
	public void test(){
		System.out.println("���Բ��Խṹ...");
	}
	
	/**
	 * �������ݿ������Ƿ�����
	 */
	@Test
	public void testDBConnection(){
		Connection conn = this.connection.getConnection();
		
		if(conn != null){
			System.out.println("���ݿ���������...");
		}else{
			System.out.println("���ݿ������쳣...");
		}
	}
	
	/**
	 * ���Բ�������
	 */
	@Test
	public void insert(){
		//��������SQL��
		String sql = " insert into test(username, password, email) values(?,?,?) ";
		
		//����ֵ�б�
		Object[] params = {"zhangxueyiou","123456","zhangxueyiou@163.com"};
		
		int affectLine = this.connection.executeUpdate(sql, params);
		
		System.out.println("Ӱ�������Ϊ: "+affectLine);
	}
	/**
	 * �������ݿ�����
	 */
	@Test
	public void update(){
		//��������SQL��
		String sql = " update test set username = ? where id = ? ";
		
		//����ֵ�б�
		Object[] params = {"xidada","4"};
		
		int affectLine = this.connection.executeUpdate(sql, params);
		
		System.out.println("Ӱ�������Ϊ: "+affectLine);
	}
	
	/**
	 * ɾ�����ݿ�����
	 */
	@Test
	public void delete(){
		//��������SQL��
		String sql = " delete from test where id > ? and id <= ? ";
		
		//����ֵ�б�
		Object[] params = {"4", "5"};
		
		int affectLine = this.connection.executeUpdate(sql, params);
		
		System.out.println("Ӱ�������Ϊ: "+affectLine);
	}
	
	/**
	 * ����ѯ���������ݷ���ResultSet��
	 */
	@Test
	public void selectToResultSet(){
		//��ѯ���ݵ�sql��
		String sql = " select * from test where id <= ? ";
		
		//�����б�
		Object[] params = {"4"};
		
		//ִ�в�ѯ
		ResultSet rs = this.connection.executeQueryRS(sql, params);
		
		//����ѯ�����ݴ�ӡ���
		try {
			while(rs.next()){
				System.out.print("��   �� :"+rs.getInt("id")+"  ");
				System.out.print("�û���:"+rs.getString("username")+"  ");
				System.out.print("�� ��   :"+rs.getString("password")+"  ");
				System.out.println("�� ��   :"+rs.getString("email")+"  ");
			}
		} catch (SQLException e) {
			System.out.println("��ѯ�������쳣�� "+e.getMessage());
		}
	}
	
	/**
	 * ��ѯ��һ��һ��
	 */
	@Test
	public void selectSingle(){
		//��ѯ���ݵ�sql��
		String sql = " select * from test where id <= ? ";
		
		//�����б�
		Object[] params = {"4"};
		
		//ִ�в�ѯ
		Object object = this.connection.executeQuerySingle(sql, params);
		
		System.out.println("��ѯ����������: "+object);
	}
	
	@Test
	public void executeQueryTest(){
		//��ѯ���ݵ�sql��
		String sql = " select * from test where id <= ? ";
		
		//�����б�
		Object[] params = {"4"};
		
		List<Object> list = this.connection.executeQuery(sql, params);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println("���:"+list.get(i));
		}
	}
}
