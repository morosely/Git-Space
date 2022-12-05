package com.oberver;

public interface ISubject {
	
	void attach(IOberver oberver);
	void detach(IOberver oberver);
	void inform(String newState);
}
