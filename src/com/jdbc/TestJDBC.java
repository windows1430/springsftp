package com.jdbc;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class TestJDBC {
	private  ResultSet result;
	private Statement statement;
	private Connection connection;
	

	
	/**
	 *  获取DB2表中BLOB字段数据
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void db2() throws SQLException,ClassNotFoundException{
		System.out.println("DB2连接开始");
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		connection=DriverManager.getConnection("jdbc:db2://192.168.70.22:50000/KOPENSYS", "db2admin", "db2admin");
		statement=connection.createStatement();
		result= statement.executeQuery("SELECT TPIF_FJ FROM  SERVICE_TPI_REPORT_FJ WHERE 1=1 AND TSIF_TICKET_ID='"+"TPIKC0212080029"+"'");
		while(result.next()){
			Blob blob=result.getBlob("TPIF_FJ");
			byte[] bytes=blob.getBytes(1, (int)blob.length());
			System.out.println(bytes.length);
		}
		result.close();
		statement.close();
		connection.close();
	}
	
	/**
	 * 获取sqlserver表中varbinary数据
	 * @throws Exception
	 */
	@Test
	public void sqlserver() throws Exception{
		System.out.println("SQLServer连接开始");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connection=DriverManager.getConnection("jdbc:sqlserver://192.168.70.113:1433;databaseName=doWord","sa","");
		statement=connection.createStatement();
		result= statement.executeQuery("select word from changWord where wid = 1");
		ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
		int data=-1;
		while(result.next()){
			InputStream stream=result.getBinaryStream(1);
			while((data=stream.read())!=-1){
				arrayOutputStream.write(data);
			}
		}
		byte[] bs=arrayOutputStream.toByteArray();
		System.out.println(bs.length);
	}

}
