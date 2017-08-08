package wh.cg.engine;

import java.io.File;
import java.util.Map;

public interface Engine {
	public void generateFile(String filename, Map<String, Object> ctx, File f, boolean overwrite);
}
