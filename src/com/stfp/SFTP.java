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
	 * SFTP连接
	 * @param username  账号
	 * @param password 密码
	 * @param port 端口号
	 * @param url 连接地址
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
	 * 上传文件
	 * @param remoteDirectory 上传的远程目录
	 * @param filename 上传之后的文件名
	 * @param channelSftp channelSftp连接对象
	 * @param inputStream 输入流
	 */
	public static void sftpUpload(String remoteDirectory,String filename,ChannelSftp channelSftp,InputStream inputStream) throws SftpException{
		
			String currenthome=channelSftp.getHome();
			remoteDirectory=currenthome+remoteDirectory;
			channelSftp.cd(remoteDirectory);
			channelSftp.put(inputStream,filename);
			System.out.println("文件上传成功");
	}
	
	/**
	 * SFTP文件移动
	 * @param channelSftp SFTP连接
	 * @param filename 需移动的文件路径名
	 * @param movepath 被移动的文件路径名
	 */
	public static void sftpMove(ChannelSftp channelSftp,String filename,String movepath){
		try {
			channelSftp.cd(channelSftp.getHome());
			channelSftp.rename(filename, movepath);
			System.out.println("移动成功");
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
