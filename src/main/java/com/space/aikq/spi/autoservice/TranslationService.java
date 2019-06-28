package com.space.aikq.spi.autoservice;

import java.util.Locale;

/**
 *  翻译接口
 * @author aikaiqiang
 * @date 2019年06月27日 16:06
 */
public interface TranslationService {
	String translate(String message, Locale from, Locale to);
}
