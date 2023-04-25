package com.shiji.thread.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 生产者消费者案例：
 */
public class TestProductorAndConsumerForLock {

	public static void main(String[] args) throws InterruptedException {
		Clerk clerk = new Clerk();

		Productor pro = new Productor(clerk);
		Consumer con = new Consumer(clerk);

		new Thread(pro, "生产者 A").start();
		new Thread(pro, "生产者 B").start();

		new Thread(con, "消费者 C").start();
		new Thread(con, "消费者 D").start();
		new Thread(con, "消费者 E").start();
	}

}

class Clerk {
	private int product = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	// 进货
	public void produce() {
		lock.lock();
		try {
			//if (product >= 1) { // 为了避免虚假唤醒，应该总是使用在循环中。
			while (product >= 1) {
				System.out.println(Thread.currentThread().getName() + " 产品已满！");

				try {
					condition.await();
				} catch (InterruptedException e) {
				}

			}
			System.out.println(Thread.currentThread().getName() + " : "+ ++product);
			condition.signalAll();
		} finally {
			lock.unlock();
		}

	}

	// 卖货
	public void sale() {
		lock.lock();
		try {
			while (product <= 0) {
				System.out.println(Thread.currentThread().getName() + " 缺货！");

				try {
					condition.await();
				} catch (InterruptedException e) {
				}
			}
			System.out.println(Thread.currentThread().getName() + " : "+ --product);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
}

// 生产者
class Productor implements Runnable {

	private Clerk clerk;

	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 15; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clerk.produce();
		}
	}
}

// 消费者
class Consumer implements Runnable {

	private Clerk clerk;

	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			clerk.sale();
		}
	}

}