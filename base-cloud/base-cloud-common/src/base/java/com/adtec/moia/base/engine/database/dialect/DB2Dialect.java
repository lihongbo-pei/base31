package com.adtec.moia.base.engine.database.dialect;

import java.util.List;

import com.adtec.moia.base.engine.database.DatabaseConstants;
import com.adtec.moia.base.engine.database.DatabaseException;
import com.adtec.moia.base.engine.database.bo.Column;
import com.adtec.moia.base.engine.database.bo.Forkey;
import com.adtec.moia.base.engine.database.bo.Index;
import com.adtec.moia.base.engine.database.bo.Table;

public class DB2Dialect extends Dialect {
	@Override
	public String getTableIndexSQL() {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.INDNAME ").append(DatabaseConstants.INDEX_NAME);
		sb.append(",a.UNIQUERULE ").append(DatabaseConstants.UNIQUERULE);
		sb.append(",b.COLNAME ").append(DatabaseConstants.COLUMN_NAME);
		sb.append(",b.COLSEQ ").append(DatabaseConstants.ORDINAL_POSITION);
		sb.append(" from SYSCAT.INDEXES a, SYSCAT.INDEXCOLUSE b ");
		sb.append("where a.INDNAME=b.INDNAME and a.INDSCHEMA=b.INDSCHEMA and a.UNIQUERULE<>'P' and a.TABSCHEMA=? and a.TABNAME=? order by a.INDNAME,b.COLSEQ");
		return sb.toString();
	}
	
	@Override
	public String getCreateTableSQL(Table table, List<Column> columns) {
		StringBuffer tableSql = new StringBuffer();
		StringBuffer primaryKeySql = new StringBuffer("PRIMARY KEY (");
		String sqlStart = "CREATE TABLE ";
		if (columns != null && columns.size()>0) {
			tableSql.append(sqlStart).append(table.getTableEname()).append("( \r\n");
			for (Column column : columns) {
				tableSql.append("	").append(column.getColumnEname()).append("     ");
				if (!"DATE".equals(column.getColumnType()) && !"INTEGER".equals(column.getColumnType())) {
					tableSql.append(column.getColumnType());
					tableSql.append("(").append(column.getColumnLen()).append(") ");
				} else {
					tableSql.append(column.getColumnType());
				}
				if (!column.isNullFlag()) {
					tableSql.append("NOT NULL");
				}
				tableSql.append(",");
				tableSql.append("\r\n");
				
				if (column.isKeyFlag()) {
					primaryKeySql.append(column.getColumnEname()).append(",");
				}
			}
		}
		if(primaryKeySql.length()>sqlStart.length()){
			primaryKeySql.deleteCharAt(primaryKeySql.length()-1);
			primaryKeySql.append(")\r\n");
			tableSql.append("	").append(primaryKeySql);
		}else{
			tableSql.deleteCharAt(tableSql.lastIndexOf(","));
		}
		
		tableSql.append(")\r\n");
		return tableSql.toString();
	}
	
	@Override
	public String getCreateColumnSQL(Table table, Column column) throws DatabaseException {
		StringBuffer sql = new StringBuffer();
		sql.append("ALTER TABLE ").append(table.getTableEname()).append(" ADD COLUMN ");
		sql.append("	\"").append(column.getColumnEname()).append("\"     ");
		if (!"DATE".equals(column.getColumnType()) && !"INTEGER".equals(column.getColumnType())) {
			sql.append(column.getColumnType());
			sql.append("(").append(column.getColumnLen()).append(") ");
		} else {
			sql.append(column.getColumnType());
		}
		if (!column.isNullFlag()) {
			sql.append("NOT NULL");
		}
		if(column.getDefaultValue() != null){
			sql.append(" DEFAULT ").append(column.getDefaultValue());
		}
		sql.append("\r\n");
		return sql.toString();
	}

	@Override
	public String getCreateIndexSQL(Table table, List<Index> indexs) throws DatabaseException {
		StringBuffer indexSql = new StringBuffer();
		if (indexs != null && indexs.size()>0) {
			for (Index idnex : indexs) {
				if (idnex.isUniqueFlag()) {
					indexSql.append("CREATE UNIQUE INDEX ");
				} else {
					indexSql.append("CREATE INDEX ");
				}
				indexSql.append(idnex.getIndexName());
				indexSql.append(" ON ").append(table.getTableEname());
				indexSql.append("(");
				String[] columns = idnex.getIndexColumns().split(",");
				for (int i = 0; i <= columns.length - 1; i++) {
					indexSql.append(columns[i]);
					if (i < columns.length - 1) {
						indexSql.append(",");
					}
				}
				indexSql.append(");\r\n");
			}
		}
		return indexSql.toString();
	}
	
	@Override
	public String getCreateForkeySQL(Table table, List<Forkey> forkeys) throws DatabaseException {
		StringBuffer constraintSql = new StringBuffer();
		if (forkeys != null && forkeys.size()>0) {
			constraintSql.append("ALTER TABLE ").append(table.getTableEname()).append("\r\n");
			for (Forkey forkey : forkeys) {
				constraintSql.append("	").append("ADD CONSTRAINT ").append(forkey.getForkeyName());
				String[] forKeyRef = forkey.getFkColumns().split(":");
				constraintSql.append(" FOREIGN KEY (").append(forKeyRef[0]).append(") ");
				constraintSql.append("REFERENCES ").append(forkey.getFkTableName()).append("(");
				constraintSql.append(forKeyRef[1]).append(")");
				constraintSql.append("\r\n");
			}
			constraintSql.append(";");
		}
		return constraintSql.toString();
	}
	
	@Override
	public String getTableSpaceSQL() throws DatabaseException {
		StringBuffer sb = new StringBuffer();
		sb.append("select tbspace ").append(DatabaseConstants.TABLESPACE_NAME);
		sb.append(" from syscat.tables where tabschema=? and tabname=?");
		return sb.toString();
	}
	
	@Override
	public boolean isUniqueIndex(String str) throws DatabaseException {
		return str.equals("U");
	}
	
	@Override
	public String getTopOneSql(String sql) throws DatabaseException {
		return "select * from (" + sql + ") temp_toponesql fetch first 1 rows only";
	}
	
	@Override
	public String getPageSql(String sql, int pageSize, int startRow) throws DatabaseException {
		startRow++;
		StringBuffer sb = new StringBuffer();
		int endRow = startRow + pageSize -1;//db2 使用 between 所以需要-1
		sb.append("select B_TEMP.* from (select A_TEMP.*,row_number() over() as RN from ");
		if (sql.toLowerCase().indexOf("from") > 0)
			sb.append("(").append(sql).append(")");
		else
			sb.append(sql);
		sb.append(" as A_TEMP order by RN asc) as B_TEMP where B_TEMP.RN between ");
		sb.append(startRow).append(" and ");
		sb.append(endRow);
		return sb.toString();
	}
	
	@Override
	public String getPageSqlWithOrderMode(String sql, int pageSize, int startRow,String orderField,String orderMode) throws DatabaseException {
		startRow++;
		StringBuffer sb = new StringBuffer();
		int endRow = startRow + pageSize -1;//db2 使用 between 所以需要-1
		sb.append("select B_TEMP.* from (select A_TEMP.*,row_number() over() as RN from ");
		if (sql.toLowerCase().indexOf("from") > 0)
			sb.append("(").append(sql).append(")");
		else
			sb.append(sql);
		sb.append(" as A_TEMP order by RN asc) as B_TEMP where B_TEMP.RN between ");
		sb.append(startRow).append(" and ");
		sb.append(endRow);
		sb.append(" ORDER BY ").append(orderField).append(" ").append(orderMode);
		return sb.toString();
	}
	
}
