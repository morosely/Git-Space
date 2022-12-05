package com.decorator;

public class PacketHTMLHeaderCreator extends PacketDecorator {

	public PacketHTMLHeaderCreator(IPacketCreator component) {
		super(component);
	}

	@Override
	public String handleContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append(this.component.handleContent());
		sb.append("</body>");
		sb.append("</html>");
		sb.append("\n");
		return sb.toString();
	}

}
