package com.tju.edu.p02;

import java.io.*;
import java.net.*;

	/*
	 * ���࣬ʵ�ִ����������������
	 */
public class Test {
	
	//����������˿ں�
	public static final int PORT = 30000;
	
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
        	//�����������׽���
        	serverSocket = new ServerSocket(PORT);
        	
        	//�ȴ�����
            while (true){
            	//��ÿͻ����׽��֣���������������߳�
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
