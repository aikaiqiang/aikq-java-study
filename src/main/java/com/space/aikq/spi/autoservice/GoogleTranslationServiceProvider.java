package com.space.aikq.spi.autoservice;

import com.google.auto.service.AutoService;

import java.util.Locale;

/**
 *  Google 翻译实现
 * @author aikaiqiang
 * @date 2019年06月27日 16:10
 */
@AutoService(TranslationService.class)
public class GoogleTranslationServiceProvider implements TranslationService{
	@Override
	public String translate(String message, Locale from, Locale to) {
		return message + " (translated by Google)";
	}
}
