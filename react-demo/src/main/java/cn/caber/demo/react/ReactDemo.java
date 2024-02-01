package cn.caber.demo.react;

import java.util.LinkedList;

public class ReactDemo {

    public static void main(String[] args) {
        MyPublisher<String> myPublisher = new MyPublisher<>(new LinkedList<String>());
        MyProcessor<String> myProcessor = new MyProcessor<String>(new LinkedList<String>());
        MySubscriber<String> mySubscriber = new MySubscriber<>();

        // 订阅
        myPublisher.subscribe(myProcessor);
        myProcessor.subscribe(mySubscriber);

        // 提交数据
        myPublisher.submit("aa");
    }
}
