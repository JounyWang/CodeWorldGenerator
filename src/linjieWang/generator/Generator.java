package wh.cg.generator;

import java.io.File;
import java.util.Map;

public interface Generator {
	public void generate(File file, String template, Map<String, Object> ctx, boolean overwrite);
}
