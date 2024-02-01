package cn.caber.demo.react;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class MySubscriber<T> implements Subscriber<T> {
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("Subscriber订阅成功，subscription:{}", subscription);
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(T t) {
        log.info("Subscriber接收到数据:{}", t);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("Subscriber报错，exception:{}", throwable.getMessage());
        log.error(ExceptionUtils.getStackTrace(throwable));
    }

    @Override
    public void onComplete() {
        log.info("Subscriber数据处理结束");
    }
}
