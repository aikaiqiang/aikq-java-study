package com.space.aikq.spi.autoservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

/**
 *  测试拓展SPI
 * @author aikaiqiang
 * @date 2019年06月27日 16:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

	@Test
	public void test() {
		ServiceLoader<TranslationService> loader = ServiceLoader.load(TranslationService.class);
		long count = StreamSupport.stream(loader.spliterator(), false).count();
		assertEquals(2, count);

		TranslationService googleService = StreamSupport.stream(loader.spliterator(), false)
				.filter(p -> p.getClass().getSimpleName().equals("GoogleTranslationServiceProvider"))
				.findFirst()
				.get();

		String message = "message";

		assertEquals(message + " (translated by Google)", googleService.translate(message, null, null));
	}


	@Test
	public void test2() {
		int n = 1;
		int count = 5;
		int out = add(n, 5);
		System.out.println(out);
	}

	private int add(int x, int count){
		if(count > 0){
			count--;
			return add((5 * x) + 1, count);
		}else {
			return x;
		}
	}
}
