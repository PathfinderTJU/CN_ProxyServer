package com.tju.edu.p02;

import java.io.*;

	/*
	 * ת���̣߳�����ת��Ŀ�����������Ӧ����
	 */
public class Transend implements Runnable{
	
	private InputStream clientinput;
	private OutputStream serveroutput;
	
	@Override
	public void run() {
		try {
			//ѭ��ת��
			while (true) {
				serveroutput.write(clientinput.read());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public InputStream getClientinput() {
		return clientinput;
	}

	public void setClientinput(InputStream clientinput) {
		this.clientinput = clientinput;
	}

	public OutputStream getServeroutput() {
		return serveroutput;
	}

	public void setServeroutput(OutputStream serveroutput) {
		this.serveroutput = serveroutput;
	}
	
	public Transend() {
		this.serveroutput = null;
		this.clientinput = null;
	}
	
	public Transend(InputStream clientinput, OutputStream serveroutput) {
		this.clientinput = clientinput;
		this.serveroutput = serveroutput;
	}
}
