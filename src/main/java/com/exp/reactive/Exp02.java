package com.exp.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

public class Exp02 {
    public static void main(String[] args) {
        AtomicReference<Subscription> sub = new AtomicReference<>();
        Flux<Integer> ints = Flux.range(1,20);
        ints.subscribeWith(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                sub.set(s);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("On next: " + integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });

        sub.get().request(3);
        sub.get().cancel();
    }
}
