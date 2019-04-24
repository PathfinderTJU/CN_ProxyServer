package com.tju.edu.p02;

import java.io.*;
import java.net.*;

	/*
	 * 代理服务器类
	 * 实现接收请求，解析地址，发送请求
	 */
public class ProxyServer implements Runnable{
	
	//私有变量：客户端套接字、目的域名、目的端口号
	private Socket clientSocket;
	private String host;
	private int port;
	
	@Override
	public void run() {
		InputStream clientinput = null;
		OutputStream clientoutput = null;
		InputStream serverinput = null;
		OutputStream serveroutput = null;
		Socket serverSocket = null;
		
		try {
			//获取客户套接字的输入输出流
			clientinput = clientSocket.getInputStream();
			clientoutput = clientSocket.getOutputStream();
			
			//采用BufferedReader从客户套接字接收数据，headline代表一行报文，header用来存储客户发送的请求报文
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientinput));
			String headline = null;
			StringBuilder header = new StringBuilder();
			
			//循环接收数据，并存入header
			while ((headline = reader.readLine()) != null) {
				header.append(headline);
				header.append("\r\n");//在每行报文后添加\r\n
				if (headline.length() == 0) {
					break;
				}
				
				//从请求报文中查找请求类型为HOST的报文，获取域名即端口号
				if (headline.split(" ")[0].contains("Host")) {
					host = headline.split(" ")[1];
				}
			}
			
			//检查HOST报文中域名是否包含端口号
			String temp[] = host.split(":");
			host = temp[0];
			
			//包含端口号，设置port
			if (temp.length > 1) {
				port = Integer.valueOf(temp[1]);
			//否则设置默认端口号80
			}else {
				port = 80;
			}
			
			//使用解析得到的域名和端口号创建目标服务器套接字
			serverSocket = new Socket(host, port);
			
			//获取目标服务器输入输出流
			serverinput = serverSocket.getInputStream();
			serveroutput = serverSocket.getOutputStream();
			
			//对于CONNECT型请求，需要先传输一段确认连接报文
			String requestType = header.substring(0, header.indexOf(" "));
			if (requestType.equals("CONNECT")) {
                serveroutput.write("HTTP/1.1 200 Connection Established\r\n".getBytes());
                serveroutput.write("\r\n".getBytes());
                serveroutput.flush();
                serveroutput.write(header.toString().getBytes());
            }else {
            	serveroutput.write(header.toString().getBytes());
            }
			
			//启动转发线程
			Transend trans = new Transend(clientinput, serveroutput);
			Thread thread = new Thread(trans);
			thread.start();
			
			while(true) {
				clientoutput.write(serverinput.read());
			}
			
		}catch(Exception e) {
				e.printStackTrace();
		}finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public ProxyServer() {
		this.clientSocket = null;
	}
	
	public ProxyServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
}
