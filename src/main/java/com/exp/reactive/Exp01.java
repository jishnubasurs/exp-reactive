package com.exp.reactive;

import com.github.javafaker.Faker;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Exp01 {
	
	static final Faker FAKER = Faker.instance();
	public static void main(String[] args) {
		
		Mono<Integer> mi = Mono.just(12)
								.map(i -> i / 2);
				;
		mi.subscribe(i -> System.out.println(i),
					err -> {System.err.println(err.getMessage());},
					() -> System.out.println("Completed")
					);
		
//		for (int j = 0; j < 10; j++) {
//			System.out.println(FAKER.name().firstName());
//		}
		
		Mono<String> mononame = Mono.fromSupplier(() -> getName());
		mononame.subscribe((s) -> System.out.println(s));
		Mono<String> mononamecall = Mono.fromCallable(() -> getName());
		mononamecall.subscribe((s) -> System.out.println(s));
		
		//Unless we are subscribing Mono the actual execution will not be done
		//Only the pipeline will be created
		getNamePublisher();
		getNamePublisher()
				.subscribeOn(Schedulers.boundedElastic())
				.subscribe(
					s -> System.out.println("IN diffrent thread: " + s)
				);
		getNamePublisher();
	}
	
	public static String getName() {
		System.out.println("Get name method executed");
		return FAKER.name().fullName();
	}
	
	public static Mono<String> getNamePublisher() {
		System.out.println("Get name method executed(pipeline started)");
		return Mono.fromSupplier(() -> {
			System.out.println("Generating name...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return FAKER.name().fullName();
		}).map(s -> s.toUpperCase());
	}
}
