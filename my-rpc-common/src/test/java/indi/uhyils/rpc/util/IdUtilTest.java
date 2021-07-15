package indi.uhyils.rpc.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月15日 22时53分
 */
class IdUtilTest {

    @org.junit.jupiter.api.Test
    void newId() throws Exception {
        IdUtil idUtil1 = new IdUtil(1L);
        IdUtil idUtil2 = new IdUtil(2L);
        IdUtil idUtil3 = new IdUtil(3L);
        final int kk1 = 1000000;
        final int kk2 = 1000000;
        final int kk3 = 1000000;
        Set<Long> test = Collections.synchronizedSet(new HashSet<>(kk1 + kk2 + kk3));
        final CountDownLatch count = new CountDownLatch(4);

        CompletableFuture<Object> objectCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            count.countDown();
            try {
                count.await();
            } catch (InterruptedException e) {
                LogUtil.error(this, e);
            }
            try {
                for (int i = 0; i < kk1; i++) {
                    long e = idUtil1.newId();
                    if (test.contains(e)) {
                        throw new RuntimeException("错误");
                    }
                    test.add(e);
                }
            } catch (Exception e) {
                LogUtil.error(this, e);
            }
            return null;
        });
        CompletableFuture<Object> objectCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            count.countDown();
            try {
                count.await();
            } catch (InterruptedException e) {
                LogUtil.error(this, e);
            }
            try {
                for (int i = 0; i < kk2; i++) {
                    long e = idUtil2.newId();
                    if (test.contains(e)) {
                        throw new RuntimeException("错误");
                    }
                    test.add(e);
                }
            } catch (Exception e) {
                LogUtil.error(this, e);
            }
            return null;
        });
        CompletableFuture<Object> objectCompletableFuture3 = CompletableFuture.supplyAsync(() -> {
            count.countDown();
            try {
                count.await();
            } catch (InterruptedException e) {
                LogUtil.error(this, e);
            }
            try {
                for (int i = 0; i < kk3; i++) {
                    long e = idUtil3.newId();
                    if (test.contains(e)) {
                        throw new RuntimeException("错误");
                    }
                    test.add(e);
                }
            } catch (Exception e) {
                LogUtil.error(this, e);
            }
            return null;
        });
        count.countDown();
        try {
            count.await();
        } catch (InterruptedException e) {
            LogUtil.error(this, e);
        }
        objectCompletableFuture1.get();
        objectCompletableFuture2.get();
        objectCompletableFuture3.get();
        RpcAssertUtil.assertTrue(test.size() == kk1 + kk2 + kk3, "idUtil生成错误");
    }
}
