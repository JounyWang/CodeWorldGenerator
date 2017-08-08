package wh.cg.job;

import java.io.File;

import wh.cg.generator.Generator;
import wh.cg.generator.TemplateGenerator;
import wh.cg.metadata.Table;

public class DefaultFileJob extends Job {

	protected Generator generator = new TemplateGenerator();

	public DefaultFileJob(String template, String fileNamePattern, String path, String namespace, boolean overwrite) {
		super(template, fileNamePattern, path, namespace, overwrite);
	}

	public DefaultFileJob(String template, String fileNamePattern, String path, String namespace) {
		super(template, fileNamePattern, path, namespace, false);
	}

	public DefaultFileJob(String template, String fileNamePattern, String path) {
		super(template, fileNamePattern, path);
	}

	@Override
	public void execute() {
		Table table = (Table) ctx.get("table");
		if (table != null) {
			String replacement = table.getName();
			String fileName = fileNamePattern.replace("*", replacement).toLowerCase();
			File file = new File(output, fileName);
			generator.generate(file, template, ctx, overwrite);
		}
	}

}
