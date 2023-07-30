package com.exp.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class Exp03 {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .subscribeWith(new Subscriber<Integer>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(100);
                        this.subscription = subscription;
                    }

                    @Override
                    public void onNext(Integer next) {
                        System.out.println(next);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("done");
                    }

                });
    }
}
