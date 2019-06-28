package com.space.aikq.spi.autoservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

/**
 *  单元测试
 * @author aikaiqiang
 * @date 2019年06月27日 16:27
 */
@Slf4j
public class TranslationServiceUnitTest {

	private ServiceLoader<TranslationService> loader;

	@Before
	public void setUp() {
		loader = ServiceLoader.load(TranslationService.class);
	}

	@Test
	public void whenServiceLoaderLoads_thenLoadsAllProviders() {
		long count = StreamSupport.stream(loader.spliterator(), false).count();
		assertEquals(2, count);
	}

	@Test
	public void whenServiceLoaderLoadsGoogleService_thenGoogleIsLoaded() {
		TranslationService googleService = StreamSupport.stream(loader.spliterator(), false)
				.filter(p -> p.getClass().getSimpleName().equals("GoogleTranslationServiceProvider"))
				.findFirst()
				.get();

		String message = "message";
		assertEquals(message + " (translated by Google)", googleService.translate(message, null, null));
	}

	@Test
	public void test2() {

	}

}
