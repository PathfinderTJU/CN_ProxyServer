package com.tju.edu.p02;

import java.io.*;
import java.net.*;

	/*
	 * 主类，实现代理服务器基本功能
	 */
public class Test {
	
	//代理服务器端口号
	public static final int PORT = 30000;
	
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
        	//创建服务器套接字
        	serverSocket = new ServerSocket(PORT);
        	
        	//等待连接
            while (true){
            	//获得客户端套接字，启动代理服务器线程
                ProxyServer proxy = new ProxyServer(serverSocket.accept());
                Thread thread = new Thread(proxy);
                thread.start();
            }
        } catch (IOException e) {
        	System.out.println("Failed to connect proxyServer.");
            e.printStackTrace();
        } finally {
        	if (serverSocket != null) {
        		try {
        			serverSocket.close();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
        }
    }
}
