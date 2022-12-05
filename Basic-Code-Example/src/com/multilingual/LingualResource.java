package com.multilingual;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class LingualResource {
	private String languageCode = "en";
	private String regionCode = "US";
	private String localeCode = "en_US";//
	private static final String FILE_NAME = "messages_en_US";

	/**
	 * @label Creates
	 */
	private static HashMap instances = new HashMap(10);
	private Locale locale = null;
	private ResourceBundle resourceBundle = null;

	/**
	 * @clientCardinality 1
	 * @supplierCardinality 0..*
	 * @directed
	 * @label Creates
	 */
	private LingualResource lnkLingualResource;

	private LingualResource(String languageCode, String regionCode) {
		this.localeCode = languageCode;
		this.regionCode = regionCode;
		localeCode = makeLocaleCode(languageCode, regionCode);

		locale = new Locale(languageCode, regionCode);
		resourceBundle = ResourceBundle.getBundle(FILE_NAME, locale);
		instances.put(makeLocaleCode(languageCode, regionCode), resourceBundle);
	}

	private LingualResource() {
		// do nothing
	}

	public synchronized static LingualResource getInstance(String language,String region) {
		if (instances.containsKey(makeLocaleCode(language, region))) {
			return (LingualResource) instances.get(makeLocaleCode(language,region));
		} else {
			return new LingualResource(language, region);
		}
	}

	public String getLocaleString(String code) {
		return resourceBundle.getString(code);
	}

	private static String makeLocaleCode(String language, String region) {
		return language + "_" + region;
	}
	
	public static void main(String[] args) {
		LingualResource ling = LingualResource.getInstance("en", "US");

		String usDollar = ling.getLocaleString("USD");

		System.out.println("USD=" + usDollar);

		LingualResource lingZh = LingualResource.getInstance("zh", "CH");

		String usDollarZh = lingZh.getLocaleString("USD");

		System.out.println("USD=" + usDollarZh);
	}
}
