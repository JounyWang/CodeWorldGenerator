package wh.cg.job;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

import wh.cg.generator.Generator;

public abstract class Job {

	protected static final Logger logger = Logger.getLogger(Job.class.getName());

	protected File output;
	protected String path;

	protected String template;
	protected String fileNamePattern;

	protected String namespace;
	protected boolean overwrite = true;

	protected Generator generator;
	protected Map<String, Object> ctx;

	public Job(String template, String fileNamePattern, String path, String namespace, boolean overwrite) {
		this.template = template;
		this.fileNamePattern = fileNamePattern;
		this.path = path;
		this.namespace = namespace;
		this.overwrite = overwrite;
	}

	public Job(String template, String fileNamePattern, String path, String namespace) {
		this(template, fileNamePattern, path, namespace, false);
	}

	public Job(String template, String fileNamePattern, String path) {
		this(template, fileNamePattern, path, null, false);
	}

	public void setContext(Map<String, Object> ctx) {
		this.ctx = ctx;
	}

	public abstract void execute();

	public void setOutput(File output) {
		this.output = output;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setFileNamePattern(String fileNamePattern) {
		this.fileNamePattern = fileNamePattern;
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}
}
