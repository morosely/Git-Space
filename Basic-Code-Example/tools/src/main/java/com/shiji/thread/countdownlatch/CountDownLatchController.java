package com.shiji.thread.countdownlatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchController {

    public static void main(String[] args) throws Exception {
//        RestTemplate restTemplate = new RestTemplate();
//        log.debug("begin");
//        ExecutorService service = Executors.newCachedThreadPool();
//        CountDownLatch latch = new CountDownLatch(4);
//        Future<Map<String,Object>> f1 = service.submit(() -> {
//            Map<String, Object> r =
//                    restTemplate.getForObject("http://localhost:8080/order/{1}", Map.class, 1);
//            return r;
//        });
//        Future<Map<String, Object>> f2 = service.submit(() -> {
//            Map<String, Object> r =
//                    restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 1);
//            return r;
//        });
//        Future<Map<String, Object>> f3 = service.submit(() -> {
//            Map<String, Object> r =
//                    restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 2);
//            return r;
//        });
//        Future<Map<String, Object>> f4 = service.submit(() -> {
//            Map<String, Object> r =
//                    restTemplate.getForObject("http://localhost:8080/logistics/{1}", Map.class, 1);
//            return r;
//        });
//        System.out.println(f1.get());
//        System.out.println(f2.get());
//        System.out.println(f3.get());
//        System.out.println(f4.get());
//        log.debug("执行完毕");
//        service.shutdown();
    }

//    @GetMapping("/order/{id}")
//    public Map<String, Object> order(@PathVariable int id) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", id);
//        map.put("total", "2300.00");
//        sleep(2000);
//        return map;
//    }
//
//    @GetMapping("/product/{id}")
//    public Map<String, Object> product(@PathVariable int id) {
//        HashMap<String, Object> map = new HashMap<>();
//        if (id == 1) {
//            map.put("name", "小爱音箱");
//            map.put("price", 300);
//        } else if (id == 2) {
//            map.put("name", "小米手机");
//            map.put("price", 2000);
//        }
//        map.put("id", id);
//        sleep(1000);
//        return map;
//    }
//
//    @GetMapping("/logistics/{id}")
//    public Map<String, Object> logistics(@PathVariable int id) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", id);
//        map.put("name", "中通快递");
//        sleep(2500);
//        return map;
//    }
//
//    private void sleep(int millis) {
//        try {
//            Thread.sleep(millis);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
