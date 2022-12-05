package com.decorator;

public abstract class PacketDecorator implements IPacketCreator {
	
	IPacketCreator component;

	public PacketDecorator(IPacketCreator component) {
		this.component = component;
	}

}
