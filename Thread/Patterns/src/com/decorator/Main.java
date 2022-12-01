package com.decorator;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPacketCreator pc = new PacketHTTPHeaderCreator(new PacketHTMLHeaderCreator(new PacketBodyCreator()));
		String content = pc.handleContent();
		//System.out.println(content);
	}

}
