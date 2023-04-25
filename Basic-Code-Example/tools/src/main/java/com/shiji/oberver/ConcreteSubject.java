package com.shiji.oberver;

import java.util.List;
import java.util.Vector;

public class ConcreteSubject implements ISubject {

	List<IOberver> vs = new Vector<IOberver>();
	
	@Override
	public void attach(IOberver oberver) {
		vs.add(oberver);
		System.out.println("Attached an observer");
	}

	@Override
	public void detach(IOberver oberver) {
		vs.remove(oberver);
		System.out.println("Delete an observer");
	}

	@Override
	public void inform(String newState) {
		for (IOberver oberver: vs ) {
			oberver.update(newState);
		}
	} 

	
}
