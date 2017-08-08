package wh.cg.loader.psms;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import wh.cg.loader.Loader;
import wh.cg.metadata.BigintColumn;
import wh.cg.metadata.Column;
import wh.cg.metadata.DateMask;
import wh.cg.metadata.DatePicker;
import wh.cg.metadata.Display;
import wh.cg.metadata.Hidden;
import wh.cg.metadata.Link;
import wh.cg.metadata.Option;
import wh.cg.metadata.Select;
import wh.cg.metadata.Table;
import wh.cg.metadata.TextArea;
import wh.cg.metadata.TextField;
import wh.cg.metadata.builder.ColumnBuilder;
import wh.cg.metadata.builder.DisplayBuilder;
import wh.cg.metadata.builder.SelectBuilder;

public class DataLoader implements Loader {

	public List<Table> load() {
		List<Table> tables = new ArrayList<Table>();
		tables.add(loadOfficer());
		tables.add(loadStuff());
		tables.add(loadCase());
		tables.add(loadTags());
		tables.add(loadZones());
		tables.add(loadRooms());
		tables.add(loadSuspect());
		tables.add(loadCameras());
		return tables;
	}

	public Table loadCameras() {
		Option[] statuses = new Option[] { new Option("1", "正常"), new Option("0", "禁用"), new Option("2", "故障") };
		List<Column> columns = new ArrayList<Column>();
		columns.add(new ColumnBuilder().name("id").remarks("标识").type(Types.BIGINT).primaryKey(true)
				.display(new Display()).build());
		columns.add(new ColumnBuilder().name("name").remarks("名称").type(Types.VARCHAR).size(64).element(new TextField())
				.display(new DisplayBuilder().element(new Link()).build()).build());
		columns.add(new ColumnBuilder().name("play_url").remarks("播放地址").type(Types.VARCHAR).size(255)
				.element(new TextField()).display(new Display()).build());
		columns.add(new ColumnBuilder().name("serial_number").remarks("硬件标识").type(Types.VARCHAR).size(255)
				.element(new TextField()).display(new Display()).build());
		columns.add(new ColumnBuilder().name("x").remarks("坐标x").type(Types.TINYINT).element(new TextField())
				.display(new DisplayBuilder().width("5%").build()).build());
		columns.add(new ColumnBuilder().name("y").remarks("坐标y").type(Types.TINYINT).element(new TextField())
				.display(new DisplayBuilder().width("5%").build()).build());
		columns.add(new ColumnBuilder().name("status").defaultValue("1")
				.display(new DisplayBuilder().mapping(statuses).build()).type(Types.TINYINT).remarks("状态").build());
		Table table = new Table("camera", "摄像机");
		table.setColumns(columns);
		return table;
	}

	public Table loadSuspect() {
		Option[] sexes = new Option[] { new Option("1", "男"), new Option("0", "女") };
		Option[] statuses = new Option[] { new Option("1", "在押"), new Option("0", "非在押") };
		List<Column> columns = new ArrayList<Column>();
		columns.add(new ColumnBuilder().name("id").remarks("标识").type(Types.BIGINT).primaryKey(true)
				.display(new Display()).build());
		columns.add(new ColumnBuilder().name("name").remarks("姓名").type(Types.VARCHAR).size(64).element(new TextField())
				.display(new DisplayBuilder().element(new Link()).build()).build());
		columns.add(new ColumnBuilder().name("sex").remarks("性别").type(Types.TINYINT).defaultValue("1")
				.element(new SelectBuilder().setOptions(sexes).build())
				.display(new DisplayBuilder().mapping(sexes).build()).build());
		columns.add(new ColumnBuilder().name("date_of_birth").remarks("出生日期").type(Types.DATE)
				.defaultValue("1980-01-01 00:00:00").element(new DateMask()).display(new DisplayBuilder().build())
				.build());
		columns.add(new ColumnBuilder().name("id_card").remarks("证件号码").type(Types.VARCHAR).size(16)
				.element(new TextField()).display(new Display()).build());
		columns.add(new ColumnBuilder().name("height").remarks("身高").type(Types.SMALLINT).element(new TextField())
				.display(new Display()).build());
		columns.add(new ColumnBuilder().name("weight").remarks("体重").type(Types.SMALLINT).element(new TextField())
				.display(new Display()).build());
		columns.add(new ColumnBuilder().name("status").display(new DisplayBuilder().mapping(statuses).build())
				.type(Types.TINYINT).remarks("状态").build());
		Table table = new Table("suspect", "嫌疑人");
		table.setColumns(columns);
		return table;
	}

	public Table loadRooms() {
		Option[] statuses = new Option[] { new Option("0", "不可用"), new Option("1", "可用") };
		Option[] types = new Option[] { new Option("0", "醒酒室"), new Option("1", "候押室"), new Option("1", "审讯室") };
		List<Column> columns = new ArrayList<Column>();
		columns.add(new ColumnBuilder().name("id").remarks("标识").type(Types.BIGINT).primaryKey(true)
				.display(new DisplayBuilder().element(new Link()).build()).build());
		columns.add(new ColumnBuilder().name("name").remarks("名称").type(Types.VARCHAR).size(64).element(new TextField())
				.display(new Display()).build());
		columns.add(
				new ColumnBuilder().name("type").remarks("类型").element(new SelectBuilder().setOptions(types).build())
						.display(new DisplayBuilder().mapping(types).build()).build());
		columns.add(new ColumnBuilder().name("status").element(new SelectBuilder().setOptions(statuses).build())
				.display(new DisplayBuilder().mapping(statuses).build()).type(Types.TINYINT).remarks("状态").build());
		Table table = new Table("room", "房间");
		table.setColumns(columns);
		return table;
	}

	public Table loadZones() {
		List<Column> columns = new ArrayList<Column>();
		columns.add(new ColumnBuilder().name("id").remarks("标识").type(Types.BIGINT).primaryKey(true)
				.display(new Display()).build());
		columns.add(new ColumnBuilder().name("name").remarks("名称").type(Types.VARCHAR).size(64).element(new TextField())
				.display(new DisplayBuilder().element(new Link()).build()).build());
		columns.add(new ColumnBuilder().name("type").remarks("类型").type(Types.TINYINT)
				.defaultValue("0").element(new SelectBuilder()
						.setOptions(new Option[] { new Option("0", "房间"), new Option("1", "走廊") }).build())
				.display(new Display()).build());
		columns.add(new ColumnBuilder().name("room_id").remarks("房间标识").type(Types.BIGINT).defaultValue("0")
				.element(new SelectBuilder().dataSource("./json/room_list.json").build()).display(new Display())
				.build());
		columns.add(new ColumnBuilder().name("points").element(new TextField()).type(Types.VARCHAR).size(255)
				.remarks("点集").display(new Display()).build());
		Table table = new Table("zone", "区域");
		table.setColumns(columns);
		return table;
	}

	public Table loadTags() {
		Option[] types = new Option[] { new Option("0", "卡片"), new Option("1", "手环") };
		Option[] statuses = new Option[] { new Option("0", "不可用"), new Option("1", "可用") };
		List<Column> columns = new ArrayList<Column>();
		columns.add(new ColumnBuilder().name("id").remarks("标识").element(new Hidden()).type(Types.BIGINT)
				.primaryKey(true).display(new Display()).build());
		columns.add(new ColumnBuilder().name("code").remarks("编号").type(Types.VARCHAR).size(32).element(new TextField())
				.display(new DisplayBuilder().element(new Link()).build()).build());
		columns.add(new ColumnBuilder().name("name").remarks("名称").type(Types.VARCHAR).size(32).element(new TextField())
				.display(new DisplayBuilder().element(new Link()).build()).build());
		columns.add(new ColumnBuilder().name("type").remarks("类型")
				.element(new SelectBuilder().setOptions(types).build())
				.display(new DisplayBuilder().mapping(types).build()).type(Types.TINYINT).defaultValue("0").build());
		columns.add(new ColumnBuilder().name("description").remarks("备注").type(Types.VARCHAR).size(255).nullable(true)
				.element(new TextField()).display(new Display()).build());
		columns.add(new ColumnBuilder().name("status").remarks("状态")
				.element(new SelectBuilder().setOptions(statuses).build())
				.display(new DisplayBuilder().mapping(statuses).build()).type(Types.TINYINT).build());
		Table table = new Table("tag", "标签");
		table.setColumns(columns);
		return table;
	}

	public Table loadOfficer() {
		Select idTypes = new SelectBuilder().setOptions(new Option[] { new Option("1", "身份证"), new Option("2", "护照") })
				.build();
		Column id = new BigintColumn("id", "标识");
		id.setPrimaryKey(true);
		id.setDisplay(new Display());
		List<Column> columns = new ArrayList<Column>();
		columns.add(id);
		columns.add(
				new ColumnBuilder().name("name").remarks("姓名").display(new DisplayBuilder().element(new Link()).build())
						.type(Types.VARCHAR).element(new TextField()).size(32).build());
		columns.add(new ColumnBuilder().name("id_type").remarks("证件类型").display(new Display()).type(Types.TINYINT)
				.element(idTypes).defaultValue(idTypes.getOptions()[0].getValue()).build());
		columns.add(new ColumnBuilder().name("id_card_number").display(new Display()).remarks("证件号码")
				.type(Types.VARCHAR).size(32).element(new TextField()).build());
		columns.add(new ColumnBuilder().name("date_of_birth").remarks("出生日期").nullable(false).defaultValue("1980-01-01")
				.type(Types.DATE).element(new DatePicker()).build());
		columns.add(new ColumnBuilder().name("tag_id").display(new Display()).remarks("标签标识").nullable(true)
				.element(new TextField()).build());
		columns.add(new ColumnBuilder().name("status").display(new Display()).remarks("状态").build());
		Table table = new Table("officer", "民警");
		table.setColumns(columns);
		return table;
	}

	public Table loadStuff() {
		Select types = new SelectBuilder().setOptions(new Option[] { new Option("1", "现金"), new Option("2", "银行卡"),
				new Option("3", "钥匙"), new Option("4", "证件") }).build();
		Hidden id = new Hidden();
		List<Column> columns = new ArrayList<Column>();
		columns.add(new ColumnBuilder().name("id").remarks("标识").unsigned(true).primaryKey(true).autoIncrement(true)
				.element(id).build());
		columns.add(new ColumnBuilder().name("suspect_id").remarks("嫌疑人").size(32).build());
		columns.add(new ColumnBuilder().name("type").type(Types.TINYINT).remarks("类型").element(types).build());
		columns.add(
				new ColumnBuilder().name("amount").type(Types.TINYINT).remarks("数量").element(new TextField()).build());
		columns.add(new ColumnBuilder().name("status").type(Types.TINYINT).remarks("状态").build());
		Table table = new Table("stuff", "个人物品");
		table.setColumns(columns);
		return table;
	}

	public Table loadCase() {
		Option[] typeOptions = new Option[] { new Option("1", "打架斗殴"), new Option("2", "盗窃"), new Option("3", "卖淫嫖娼") };
		Option[] sourceOptions = new Option[] { new Option("1", "110"), new Option("2", "自首") };
		Select types = new SelectBuilder().setOptions(typeOptions).build();
		Select sources = new SelectBuilder().setOptions(sourceOptions).build();
		Hidden id = new Hidden();
		TextArea title = new TextArea();
		List<Column> columns = new ArrayList<Column>();
		columns.add(new ColumnBuilder().name("id").remarks("标识").type(Types.BIGINT).unsigned(true).primaryKey(true)
				.autoIncrement(true).element(id).display(new DisplayBuilder().width("5%").style("text-center").build())
				.build());
		columns.add(new ColumnBuilder().name("title").type(Types.VARCHAR).size(255).remarks("名称").nullable(true)
				.element(title).display(new DisplayBuilder().element(new Link()).build()).build());
		columns.add(new ColumnBuilder().name("type").remarks("类型").element(types)
				.display(new DisplayBuilder().mapping(typeOptions).width("20%").build()).build());
		columns.add(new ColumnBuilder().name("source").remarks("来源")
				.display(new DisplayBuilder().mapping(sourceOptions).width("16%").build()).element(sources).build());
		columns.add(new ColumnBuilder().name("status").remarks("状态")
				.display(new DisplayBuilder().mapping("/json/case.status.options.json").width("8%").build()).build());
		columns.add(new ColumnBuilder().name("created_on").type(Types.DATE).remarks("创建时间").display(new Display("15%"))
				.build());
		columns.add(new ColumnBuilder().name("created_by").remarks("创建人").build());
		columns.add(new ColumnBuilder().name("last_modified_on").type(Types.DATE).remarks("最后修改时间").build());
		columns.add(new ColumnBuilder().name("last_modified_by").remarks("修改人").build());
		Table table = new Table("case", "案件");
		table.setColumns(columns);
		return table;
	}

}
