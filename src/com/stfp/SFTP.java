package com.stfp;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.analytic.Propery;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public final class SFTP {
	
	/**
	 * SFTP����
	 * @param username  �˺�
	 * @param password ����
	 * @param port �˿ں�
	 * @param url ���ӵ�ַ
	 * @return
	 */
	public  static ChannelSftp sftpConnect(String username,String password,int port,String host) throws JSchException{
			ChannelSftp channelSftp=null;
			JSch jsch=new JSch();
			Session session=jsch.getSession(username, host, port);
			session.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			session.setConfig(sshConfig);
			session.connect();
			Channel channel=session.openChannel("sftp");
			channel.connect();
			channelSftp=(ChannelSftp)channel;
			return channelSftp;
	}
	
	/**
	 * �ϴ��ļ�
	 * @param remoteDirectory �ϴ���Զ��Ŀ¼
	 * @param filename �ϴ�֮����ļ���
	 * @param channelSftp channelSftp���Ӷ���
	 * @param inputStream ������
	 */
	public static void sftpUpload(String remoteDirectory,String filename,ChannelSftp channelSftp,InputStream inputStream) throws SftpException{
		
			String currenthome=channelSftp.getHome();
			remoteDirectory=currenthome+remoteDirectory;
			channelSftp.cd(remoteDirectory);
			channelSftp.put(inputStream,filename);
			System.out.println("�ļ��ϴ��ɹ�");
	}
	
	/**
	 * SFTP�ļ��ƶ�
	 * @param channelSftp SFTP����
	 * @param filename ���ƶ����ļ�·����
	 * @param movepath ���ƶ����ļ�·����
	 */
	public static void sftpMove(ChannelSftp channelSftp,String filename,String movepath){
		try {
			channelSftp.cd(channelSftp.getHome());
			channelSftp.rename(filename, movepath);
			System.out.println("�ƶ��ɹ�");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void connectTest() throws Exception{
		String filename="temp/KMKC02090800081.JPG";
		String movepath="data/KMKC02090800081.JPG";
		Map<String,String> analyticmap=Propery.analytic("sftp.properties");
		String username=analyticmap.get("user");
		String password=analyticmap.get("password");
		String host=analyticmap.get("host");
		String filepath=analyticmap.get("filepath");
		int port=Integer.valueOf(analyticmap.get("port"));
		ChannelSftp channelSftp=SFTP.sftpConnect(username, password, port, host);
		/*System.out.println(channelSftp.getHome());
		System.out.println(channelSftp.pwd());
		channelSftp.cd("data");
		System.out.println(channelSftp.pwd());
		System.out.println(channelSftp.getHome());
		channelSftp.cd(channelSftp.getHome());
		System.out.println(channelSftp.pwd());*/
		sftpMove(channelSftp, filename, movepath);
	}
}
