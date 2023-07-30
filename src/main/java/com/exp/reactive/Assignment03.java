package com.exp.reactive;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Assignment03 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger start = new AtomicInteger(100);
        AtomicReference<Subscription> sub = new AtomicReference<>();
        Flux<Integer> ranged = Flux.interval(Duration.ofSeconds(1))
                .map(i -> {
                    int v = start.get() + Faker.instance().random().nextInt(-5, 5);
                    start.set(v);
                    return v;
                });
        ranged.subscribeWith(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
                sub.set(s);
            }

            @Override
            public void onNext(Integer aDouble) {
                sub.get().request(1);
                System.out.println("Current stock price: " + start.get());
                if (start.get() <= 93 || start.get() >= 103) {
                    sub.get().cancel();
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
                latch.countDown();
            }
        });
//        System.out.println();

//        for (int i = 0; i < 100; i++) {
//            sub.get().request(1);
//        }

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        latch.await();
    }

    private static double getRandomNumber() {
        return Math.random() * (6 + 6) + -6;
    }
}
