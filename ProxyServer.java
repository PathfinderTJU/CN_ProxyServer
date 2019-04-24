package com.tju.edu.p02;

import java.io.*;
import java.net.*;

	/*
	 * �����������
	 * ʵ�ֽ������󣬽�����ַ����������
	 */
public class ProxyServer implements Runnable{
	
	//˽�б������ͻ����׽��֡�Ŀ��������Ŀ�Ķ˿ں�
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
			//��ȡ�ͻ��׽��ֵ����������
			clientinput = clientSocket.getInputStream();
			clientoutput = clientSocket.getOutputStream();
			
			//����BufferedReader�ӿͻ��׽��ֽ������ݣ�headline����һ�б��ģ�header�����洢�ͻ����͵�������
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientinput));
			String headline = null;
			StringBuilder header = new StringBuilder();
			
			//ѭ���������ݣ�������header
			while ((headline = reader.readLine()) != null) {
				header.append(headline);
				header.append("\r\n");//��ÿ�б��ĺ����\r\n
				if (headline.length() == 0) {
					break;
				}
				
				//���������в�����������ΪHOST�ı��ģ���ȡ�������˿ں�
				if (headline.split(" ")[0].contains("Host")) {
					host = headline.split(" ")[1];
				}
			}
			
			//���HOST�����������Ƿ�����˿ں�
			String temp[] = host.split(":");
			host = temp[0];
			
			//�����˿ںţ�����port
			if (temp.length > 1) {
				port = Integer.valueOf(temp[1]);
			//��������Ĭ�϶˿ں�80
			}else {
				port = 80;
			}
			
			//ʹ�ý����õ��������Ͷ˿ںŴ���Ŀ��������׽���
			serverSocket = new Socket(host, port);
			
			//��ȡĿ����������������
			serverinput = serverSocket.getInputStream();
			serveroutput = serverSocket.getOutputStream();
			
			//����CONNECT��������Ҫ�ȴ���һ��ȷ�����ӱ���
			String requestType = header.substring(0, header.indexOf(" "));
			if (requestType.equals("CONNECT")) {
                serveroutput.write("HTTP/1.1 200 Connection Established\r\n".getBytes());
                serveroutput.write("\r\n".getBytes());
                serveroutput.flush();
                serveroutput.write(header.toString().getBytes());
            }else {
            	serveroutput.write(header.toString().getBytes());
            }
			
			//����ת���߳�
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
