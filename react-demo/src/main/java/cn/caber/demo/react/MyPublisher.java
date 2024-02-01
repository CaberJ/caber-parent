package cn.caber.demo.react;

import cn.caber.commons.thread.CaberTransmittableRunnable;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.LinkedList;

@Slf4j
public class MyPublisher<T> implements Publisher<T> {

    private final LinkedList<T> linkedList;

    public MyPublisher(LinkedList<T> linkedList) {
        this.linkedList = linkedList;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        log.info("Publisher发起订阅，subscriber:{}", subscriber);
        subscriber.onSubscribe(new MySubscription());
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

    public void submit(T t) {
        linkedList.addFirst(t);
    }


}
