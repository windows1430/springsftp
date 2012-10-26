package com.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.analytic.Propery;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.stfp.SFTP;
import com.test.model.DaoService;
import com.test.model.Service_tsi_report_fj;


public class ConnectSFTP extends HttpServlet{
	private String nameSpace="Service_tsi_report_fjSpace.";
	private List<Service_tsi_report_fj> list;
	private ChannelSftp channelSftp;
	private DaoService daoService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		daoService=(DaoService)context.getBean("daoService");
		Map<String,String> analyticmap=Propery.analytic("sftp.properties");
		String username=analyticmap.get("user");
		String password=analyticmap.get("password");
		String host=analyticmap.get("host");
		String filepath=analyticmap.get("filepath");
		int port=Integer.valueOf(analyticmap.get("port"));
		try {
			channelSftp=SFTP.sftpConnect(username, password, port, host);
			Map<String,String> map=new HashMap<String, String>();
			map.put("TSIF_TICKET_ID", "KMKC0209080008");
			list=daoService.getList(map);
			putList(list, filepath,channelSftp);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void putList(List<Service_tsi_report_fj> list,String filepath,ChannelSftp channelSftp){
		//存放List中Service_tsi_report_fj对象
		Service_tsi_report_fj service_tsi_report_fj;
		Iterator<Service_tsi_report_fj> iterator=list.iterator();
		int i;
		String suffix;
		StringBuffer filename=new StringBuffer();
		while(iterator.hasNext()){
			service_tsi_report_fj=iterator.next();
			String filetype=service_tsi_report_fj.getTsif_fj_name();
			i=filetype.indexOf(".");
			//存放此文件的后缀名
			suffix=filetype.substring(i);
			//SFTP上创建的文件名
			filename.append(service_tsi_report_fj.getTsif_ticket_id()+service_tsi_report_fj.getTsif_serial_no()+suffix);
			byte[] bytes=service_tsi_report_fj.getTsif_fj();
			InputStream inputStream=new ByteArrayInputStream(bytes);
			try {
				SFTP.sftpUpload(filepath, filename.toString(), channelSftp, inputStream);
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//清空filename内容
			filename.setLength(0);
		}
	}
}
