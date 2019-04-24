package com.tju.edu.p02;

import java.io.*;

	/*
	 * 转发线程，用于转发目标服务器的响应数据
	 */
public class Transend implements Runnable{
	
	private InputStream clientinput;
	private OutputStream serveroutput;
	
	@Override
	public void run() {
		try {
			//循环转发
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
