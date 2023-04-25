package com.shiji.lock;

public class OptimismLockFactory {

	private static OptimismLockFactory instance = new OptimismLockFactory();

    private final static Object lock = new Object();

    private long version = 0L;

    private Object result = null;

	//私有构造函数
	private OptimismLockFactory(){
	
	};

	public  static OptimismLockFactory getInstance(){     
	   return instance;
	 }

	//update ACCOUNT set VERSION = VERSION+1 where ACC_ID = #id# AND VERSION=#version#  
	 /*if (count <= 0) {//如果更新执行返回的数量是 0 表示产生并发修改了，需要重新获得最新的数据后再进行更新操作 
	     // throw new SQLException("乐观锁异常");  
	     throw new OptimisticLockException("乐观锁异常");//这里把javaee.jar包导入了  
	 } */ 
	public Object getObject(){
		//取参数实际版本号（数据库，或远程等地方）
		long remoteVersion = 1L;
		//记录当前线程取得缓存的版本号
		long localVersion = version;

		/*
		 如果缓存尚未更新过本线程取得当前缓存版本号已经过时 ，
		则进行缓存更新操作，此条件虽在并发控制内，
		就是为了减少并发时的锁竞争，被称为乐观锁定
		（言外之意就是认为此时只有本线程访问）*/

		if(localVersion==0 || localVersion < remoteVersion){

			synchronized (lock){
			/*
	       	由于是乐观认为是只有本线程访问，实际进行更新时，再进行一次版本的比较
			比较实际缓存版本与本线程取得版本相同，在证明确实只有本线程访问，
			则进行更新操作，负责返回最新的版本*/
				if(this.version==localVersion){
					//更新操作 result=...;
					return result;
				}else{
					return result;
				}
			}
		}else{
			return result;
		}

   }
	
}
