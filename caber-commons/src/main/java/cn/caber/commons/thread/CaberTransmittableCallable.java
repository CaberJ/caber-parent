package cn.caber.commons.thread;

import com.alibaba.ttl.TtlCallable;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface CaberTransmittableCallable<V> extends Callable<V> {

    @Override
    default V call() throws Exception {
        TtlCallable<V> vTtlCallable = TtlCallable.get(this::doCall);
        return vTtlCallable.call();
    }

    V doCall() throws Exception;

}
