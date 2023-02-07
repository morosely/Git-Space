package fills.thread;

public class DaemonThreadUtil extends Thread{
	
	private Long startTime;
	
	private Thread thread;
	
	private Long timeOut;
	
	public DaemonThreadUtil(Thread thread,Long timeOut) {
		this.thread = thread;
		this.timeOut = timeOut;
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		try{
			thread.start();
			while(thread.isAlive()&& (System.currentTimeMillis()-startTime) < timeOut){
			}
			if(thread.isAlive()){
				if(thread instanceof FileSearchThreadUtil){
					System.out.println(thread.getName()+"_"+((FileSearchThreadUtil)thread).file.getName()+"超时强制中断"+(System.currentTimeMillis()-startTime));
				}else{
					System.out.println(thread.getName()+"超时强制中断"+(System.currentTimeMillis()-startTime));
				}
				thread.stop();
			}
		}catch(Exception e){
			return;
		}
		return;
	}
	
	
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
					System.out.println("我要验证是否会强制中断线程1");
					ThreadUtil.sleep(5000L);
					System.out.println("我要验证是否会强制中断线程2");
			}
		});
		DaemonThreadUtil daemonThreadUtil = new DaemonThreadUtil(t, 1000L);
		daemonThreadUtil.setDaemon(true);
		daemonThreadUtil.start();
		while(true);
	}

}
