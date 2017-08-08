package wh.cg;

import org.apache.commons.lang.StringUtils;

public class FileNameBuilder {
	
	public static String buildClassName(String name) {
		String[] words = name.split("[^a-zA-Z]");
		for (int i = 0; i < words.length; i++) {
			words[i] = StringUtils.capitalize(words[i]);
		}
		return StringUtils.join(words);
	}
}
