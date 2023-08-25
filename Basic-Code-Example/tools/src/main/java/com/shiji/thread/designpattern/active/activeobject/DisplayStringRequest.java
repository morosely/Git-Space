package com.shiji.thread.designpattern.active.activeobject;

class DisplayStringRequest extends MethodRequest<Object> {
    private final String string;
    public DisplayStringRequest(Servant servant, String string) {
        super(servant, null);
        this.string = string;
    }
    public void execute() {
        servant.displayString(string);
    }
}
