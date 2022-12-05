package com.decorator;

public class PacketHTTPHeaderCreator extends PacketDecorator {

	public PacketHTTPHeaderCreator(IPacketCreator component) {
		super(component);
	}

	@Override
	public String handleContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("Cache-Control:no-cache");
		sb.append("\n");
		sb.append("Date:2014-12-24");
		sb.append("\n");
		sb.append(this.component.handleContent());
		return sb.toString();
	}

}
