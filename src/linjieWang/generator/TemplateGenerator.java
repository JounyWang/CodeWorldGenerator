package wh.cg.generator;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

import wh.cg.engine.Engine;
import wh.cg.engine.FreeMarkerEngine;

public class TemplateGenerator implements Generator {
	private static final Logger logger = Logger.getLogger(TemplateGenerator.class.getName());

	private Engine engine = new FreeMarkerEngine();

	@Override
	public void generate(File file, String template, Map<String, Object> ctx, boolean overwrite) {
		logger.fine("Generating, " + file.getAbsolutePath() + " !!!");
		engine.generateFile(template, ctx, file, overwrite);
	}
}
