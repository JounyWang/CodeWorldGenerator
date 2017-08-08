package wh.cg.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.logging.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerEngine implements Engine {

	private static final Logger logger = Logger.getLogger(FreeMarkerEngine.class.getName());

	private String input = "/Users/Hao/Documents/Workspace/CodeGenerator/templates";

	private Configuration config;

	public FreeMarkerEngine() {
		config = new Configuration(Configuration.VERSION_2_3_25);
		try {
			config.setDirectoryForTemplateLoading(new File(input));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void generateFile(String template, Map<String, Object> ctx, File file, boolean overwrite) {
		if (file.exists()) {
			if (overwrite) {
				logger.fine(file.getAbsolutePath() + " overwrited.");
			} else {
				logger.fine(file.getAbsolutePath() + " already exists. skipped!!!");
				return;
			}
		}
		Template t = null;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			t = config.getTemplate(template);
			t.process(ctx, bw);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
