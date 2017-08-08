package wh.cg;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import wh.cg.job.DefaultFileJob;
import wh.cg.job.JavaClassJob;
import wh.cg.job.Job;
import wh.cg.loader.Loader;
import wh.cg.loader.psms.DataLoader;
import wh.cg.loader.psms.JSONDataLoader;
import wh.cg.metadata.Table;

public class CodeGenerator {

	private static final Logger logger = Logger.getLogger(CodeGenerator.class.getName());

	private List<Table> tables;

	private File root;

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public void generate(List<Job> jobs) {
		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("now", new java.sql.Date(System.currentTimeMillis()));
		ctx.put("author", "CodeGenerator");
		ctx.put("random", Math.round(100000 * Math.random()));
		for (Table table : tables) {
			ctx.put("table", table);
			for (Job job : jobs) {
				File dir = root;
				String path = job.getPath();
				if (path != null) {
					String[] dirNames = path.split("/");
					if (dirNames.length != 0) {
						for (String dirName : dirNames) {
							if (dirName.equals("")) {
								continue;
							}
							dir = new File(dir, dirName);
							if (!dir.exists()) {
								if (dir.mkdir()) {
									logger.info(dir.getAbsolutePath() + " created.");
								}
							}
						}
					}
				}
				job.setOutput(dir);
				job.setContext(ctx);
				job.execute();
			}
		}
	}

	public static void main(String[] args) {
		File root = new File("/Users/Hao/Documents/Workspace/PSMS/");
		if (root.exists() && root.isDirectory()) {

			List<Job> jobs1 = new ArrayList<Job>();
			jobs1.add(new DefaultFileJob("defination.json.ftl", "*.def.json", "/defination", null, true));
			// jobs1.add(new DefaultFileJob("schema.json.ftl", "*_schema.json",
			// "/prototype/schema", null, false));
			// jobs1.add(new DefaultFileJob("form_1.0.html.ftl", "*_form.html",
			// "/prototype/pages", null, true));
			// jobs1.add(new DefaultFileJob("list_1.0.html.ftl", "*_list.html",
			// "/prototype/pages", null, true));

			List<Job> jobs2 = new ArrayList<Job>();
			jobs2.add(new DefaultFileJob("sql.ftl", "*.sql", "/sql", null, true));
			jobs2.add(new DefaultFileJob("form_2.0.html.ftl", "*_form.html", "/WebContent/pages", null, true));
			jobs2.add(new DefaultFileJob("list_2.0.html.ftl", "*_list.html", "/WebContent/pages", null, true));
			jobs2.add(new DefaultFileJob("detail.html.ftl", "*.html", "/WebContent/pages", null, true));
			jobs2.add(new JavaClassJob("Entity.java.ftl", "*.java", "/src", "com.sihuatech.psms.entity"));
			jobs2.add(new JavaClassJob("SelectProvider.java.ftl", "*SelectProvider.java", "/src",
					"com.sihuatech.psms.provider"));
			jobs2.add(new JavaClassJob("Mapper.java.ftl", "*Mapper.java", "/src", "com.sihuatech.psms.mapper"));
			jobs2.add(new JavaClassJob("Service.java.ftl", "*Service.java", "/src", "com.sihuatech.psms.service"));
			jobs2.add(new JavaClassJob("Controller.java.ftl", "*Controller.java", "/src",
					"com.sihuatech.psms.controller"));
			// jobs2.add(new DefaultFileJob("list.json.ftl", "*_list.json",
			// "/prototype/json", null, true));
			// jobs2.add(new DefaultFileJob("detail.json.ftl", "*.json",
			// "/prototype/json", null, true));

			Loader loader2 = new JSONDataLoader("/Users/Hao/Documents/Workspace/PSMS/defination/");
			Loader loader1 = new DataLoader();

			CodeGenerator cg = new CodeGenerator();
			cg.setRoot(root);
			cg.setTables(loader1.load());
			cg.generate(jobs1);
			cg.setTables(loader2.load());
			cg.generate(jobs2);
		}
	}
}
