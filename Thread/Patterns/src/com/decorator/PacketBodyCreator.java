package com.decorator;

public class PacketBodyCreator implements IPacketCreator {

	@Override
	public String handleContent() {
		return "Content of packet...";
	}

}
