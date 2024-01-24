package cn.caber.commons.thread;

import com.alibaba.ttl.TtlRunnable;

@FunctionalInterface
public interface CaberTransmittableRunnable extends Runnable {

    @Override
    default void run() {
        TtlRunnable ttlRunnable = TtlRunnable.get(this::doRun);
        ttlRunnable.run();
    }

    void doRun();

}
