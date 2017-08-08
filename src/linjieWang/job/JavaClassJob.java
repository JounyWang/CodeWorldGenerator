package wh.cg.job;

import java.io.File;

import wh.cg.FileNameBuilder;
import wh.cg.generator.Generator;
import wh.cg.generator.TemplateGenerator;
import wh.cg.metadata.Table;

public class JavaClassJob extends Job {

	protected Generator generator = new TemplateGenerator();

	protected boolean overwrite = true;

	public JavaClassJob(String template, String fileNamePattern, String path, String namespace) {
		super(template, fileNamePattern, path, namespace);
	}

	@Override
	public void execute() {
		Table table = (Table) ctx.get("table");
		if (table == null) {
			return;
		}
		logger.info("XXX --- " + output.getAbsolutePath());
		File parent = output;
		if (parent.isDirectory() && parent.exists()) {
			String[] dirNames = namespace.split("\\.");
			for (String dirName : dirNames) {
				parent = new File(parent, dirName);
				if (!parent.exists()) {
					if (parent.mkdir()) {
						logger.info("***Dir: " + parent.getAbsolutePath() + " created.");
					}
				}
			}
		}
		logger.info("### --- parent: " + parent.getAbsolutePath());
		ctx.put("namespace", namespace);
		String tableName = table.getName();
		String fileName = fileNamePattern.replace("*", FileNameBuilder.buildClassName(tableName));
		File file = new File(parent, fileName);
		generator.generate(file, template, ctx, overwrite);
	}

}
