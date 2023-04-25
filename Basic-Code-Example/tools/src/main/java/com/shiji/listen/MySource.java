package com.shiji.listen;

import java.util.Vector;
//做一个事件发生者，此类中保存有所有在这里报到的类的引用
public class MySource {

	private int value;
	Vector listeners = new Vector();

	public void addListener(Listenable   l){
		listeners.add(l);
	}

	public void setValue(int value) {
		this.value = value;
		fireChanged();
	}

	private void fireChanged() {
		MyEvnet e = new MyEvnet();
		e.setValue(value);
		for (int i = 0; i < listeners.size(); i++) {
			Listenable l = (Listenable) listeners.elementAt(i);
			l.enventChanged(e);
		}
	}
}
