package com.shiji.callback;

public abstract class CallBackBody {

	void onSuccess(Object context) {
        System.out.println("onSuccess");
    }
 
    void onFailure(Object context) {
        System.out.println("onFailure");
    }
 
    abstract void execute(Object context) throws Exception;
}
