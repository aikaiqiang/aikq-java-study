package com.space.aikq.spi.autoservice;

import com.google.auto.service.AutoService;

import java.util.Locale;

/**
 *  Bing 翻译实现
 * @author aikaiqiang
 * @date 2019年06月27日 16:09
 */
@AutoService(TranslationService.class)
public class BingTranslationServiceProvider implements TranslationService {
	@Override
	public String translate(String message, Locale from, Locale to) {
		return message + " (translated by Bing)";
	}
}
