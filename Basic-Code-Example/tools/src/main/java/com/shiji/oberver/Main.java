package com.shiji.oberver;

import javax.swing.AbstractButton;

public class Main {

	public static void main(String[] args) {
		ISubject subject = new ConcreteSubject();
		IOberver oberver = new ConcreteOberver();
		subject.attach(oberver);
		subject.inform("New");
	}

}
