package com.exp.reactive;

import com.github.javafaker.Faker;
import reactor.core.publisher.Flux;

public class Assignment02 {
    public static void main(String[] args) {
        Flux<Integer> rangedNames = Flux.range(1, 10);
        rangedNames.subscribe(
                (i) -> System.out.println(Faker.instance().name().fullName())
        );
    }
}
