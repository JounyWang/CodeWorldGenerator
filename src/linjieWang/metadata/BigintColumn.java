package wh.cg.metadata;

import java.sql.Types;

public class BigintColumn extends Column {
	protected int type = Types.BIGINT;

	public BigintColumn(String a, String b) {
		super(a, b);
		this.type = Types.BIGINT;
	}

}
