package cn.caber.demo.react;

import cn.caber.commons.thread.CaberTransmittableRunnable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.LinkedList;

@Slf4j
public class MyProcessor<T> implements Processor<T, T> {
    private Subscription subscription;
    private Subscriber<? super T> subscriber;
    private final LinkedList<T> linkedList;

    public MyProcessor(LinkedList<T> linkedList) {
        this.linkedList = linkedList;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        log.info("Processor发起订阅，subscriber:{}", subscriber);
        this.subscriber = subscriber;
        this.subscriber.onSubscribe(new MySubscription());
        CaberTransmittableRunnable runnable = new CaberTransmittableRunnable() {
            @Override
            public void doRun() {
                log.info("开始订阅，subscriber:{}", subscriber);
                while (true) {
                    if (linkedList.isEmpty()) {
                        continue;
                    }
                    subscriber.onNext(linkedList.removeLast());
                }
            }
        };
        new Thread(runnable).start();

    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("Processor订阅成功，subscription:{}", subscription);
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(T t) {
        log.info("Processor接收到数据:{}", t);
        this.subscription.request(1);
        submit(t);
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("Processor报错，exception:{}", throwable.getMessage());
        log.error(ExceptionUtils.getStackTrace(throwable));
    }

    @Override
    public void onComplete() {
        log.info("Processor数据处理结束");
    }

    public void submit(T t) {
        linkedList.addFirst(t);
    }
}
