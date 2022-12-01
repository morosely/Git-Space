package com.efuture.springboot.transaction;

import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

@RocketMQTransactionListener(txProducerGroup = "springTransactionGroup")
public class SpringLocalTransactionListener implements RocketMQLocalTransactionListener {

	private static Map<String, RocketMQLocalTransactionState> STATE_MAP = new HashMap<>();

	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
		String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
		try {
			System.out.println("执行操作1");
			Thread.sleep(500);
			System.out.println("执行操作2");
			Thread.sleep(800);

			STATE_MAP.put(transId, RocketMQLocalTransactionState.COMMIT);
			return RocketMQLocalTransactionState.COMMIT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		STATE_MAP.put(transId, RocketMQLocalTransactionState.ROLLBACK);
		return RocketMQLocalTransactionState.ROLLBACK;
	}

	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
		String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
		System.out.println("回查消息 -> transId = " + transId + ", state = " + STATE_MAP.get(transId));
		return STATE_MAP.get(transId);
	}

}
