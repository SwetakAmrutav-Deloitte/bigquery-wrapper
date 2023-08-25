package com.deloitte.legend.engine.bigquery;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

public class BigQueryResultSet implements ResultSet {

	private final TableResult tableResult;
	private int currentRow = 0;
	private final Iterator<FieldValueList> rowList;
	ArrayList<FieldValueList> arrayList = new ArrayList<FieldValueList>();
	private final long totalRows;
	private final int columnCount;

	public BigQueryResultSet(TableResult tableResult) throws SQLException {
		this.tableResult = tableResult;
		totalRows = tableResult.getTotalRows();
		columnCount = getMetaData().getColumnCount();
		rowList = tableResult.getValues().iterator();
		while (rowList.hasNext()) {
			arrayList.add(rowList.next());
		}
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean next() throws SQLException {
		if (!arrayList.isEmpty() && currentRow < tableResult.getTotalRows()) {
			currentRow++;
			return true;
		}
		return false;
	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public String getString(int columnIndex) throws SQLException {
		String columnValue = null;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				columnValue = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				columnValue = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
		}
		return columnValue;
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		boolean columnValue = false;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				columnValue = arrayList.get(currentRow).get(columnIndex - 1).getBooleanValue();
			} else {
				columnValue = arrayList.get(currentRow - 1).get(columnIndex - 1).getBooleanValue();
			}
		}
		return columnValue;
	}

	public byte getByte(int columnIndex) throws SQLException {
		byte columnValue = 0;
		String byteStr = null;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				byteStr = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				byteStr = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = Byte.parseByte(byteStr);
		}
		return columnValue;
	}

	public short getShort(int columnIndex) throws SQLException {
		short columnValue = 0;
		String shortStr = null;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				shortStr = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				shortStr = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = Short.parseShort(shortStr);
		}
		return columnValue;
	}

	public int getInt(int columnIndex) throws SQLException {
		int columnValue = 0;
		String integerStr = null;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				integerStr = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				integerStr = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = Integer.parseInt(integerStr);
		}
		return columnValue;
	}

	public long getLong(int columnIndex) throws SQLException {
		long columnValue = 0;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				columnValue = arrayList.get(currentRow).get(columnIndex - 1).getLongValue();
			} else {
				columnValue = arrayList.get(currentRow - 1).get(columnIndex - 1).getLongValue();
			}
		}
		return columnValue;
	}

	public float getFloat(int columnIndex) throws SQLException {
		float columnValue = 0;
		String floatValue = null;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				floatValue = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				floatValue = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = Float.valueOf(floatValue);
		}
		return columnValue;
	}

	public double getDouble(int columnIndex) throws SQLException {
		double columnValue = 0;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				columnValue = arrayList.get(currentRow).get(columnIndex - 1).getDoubleValue();
			} else {
				columnValue = arrayList.get(currentRow - 1).get(columnIndex - 1).getDoubleValue();
			}
		}
		return columnValue;
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		BigDecimal columnValue = null;
		String floatValue = null;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				floatValue = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				floatValue = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = new BigDecimal(floatValue);
		}
		return columnValue;
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		byte[] columnValue = null;
		System.out.println("Method: getBytes");
		if (columnIndex <= columnCount) {
			columnValue = arrayList.get(currentRow).get(columnIndex - 1).getBytesValue();
		}
		return columnValue;
	}

	public Date getDate(int columnIndex) throws SQLException {
		Date columnValue = null;
		String dateStr = null;
		System.out.println("Method: getDate");
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				dateStr = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				dateStr = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = Date.valueOf(dateStr);
			System.out.println(columnValue);
		}

		return columnValue;
	}

	public Time getTime(int columnIndex) throws SQLException {
		Time columnValue = null;
		String timesStr = null;
		System.out.println("Method: getTime");
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				timesStr = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				timesStr = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = Time.valueOf(timesStr);
		}
		return columnValue;
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		Timestamp columnValue = null;
		String timestampStr = null;
		System.out.println("Method: getTimestamp");
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				timestampStr = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				timestampStr = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = Timestamp.valueOf(timestampStr);
		}
		return columnValue;
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		InputStream columnValue = null;
		if (columnIndex <= columnCount) {
			columnValue = (InputStream) arrayList.get(currentRow).get(columnIndex - 1).getValue();
		}
		return columnValue;
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		InputStream columnValue = null;
		if (columnIndex <= columnCount) {
			columnValue = (InputStream) arrayList.get(currentRow).get(columnIndex - 1).getValue();
		}
		return columnValue;
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		InputStream columnValue = null;
		if (columnIndex <= columnCount) {
			columnValue = (InputStream) arrayList.get(currentRow).get(columnIndex - 1).getValue();
		}
		return columnValue;
	}

	public String getString(String columnLabel) throws SQLException {
		String columnValue = null;
		if (columnLabel != null) {
			if (currentRow == 0) {
				columnValue = arrayList.get(currentRow).get(columnLabel).getStringValue();
			} else {
				columnValue = arrayList.get(currentRow - 1).get(columnLabel).getStringValue();
			}
		}
		return columnValue;
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		boolean columnValue = false;
		if (columnLabel != null) {
			columnValue = arrayList.get(currentRow - 1).get(columnLabel).getBooleanValue();
		}
		return columnValue;
	}

	public byte getByte(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public short getShort(String columnLabel) throws SQLException {
		short columnValue = 0;
		if (columnLabel != null) {
			columnValue = (Short) arrayList.get(currentRow).get(columnLabel).getValue();
		}
		return columnValue;
	}

	public int getInt(String columnLabel) throws SQLException {
		int columnValue = 0;
		if (columnLabel != null) {
			columnValue = (Integer) arrayList.get(currentRow).get(columnLabel).getValue();
		}
		return columnValue;
	}

	public long getLong(String columnLabel) throws SQLException {
		long columnValue = 0;
		if (columnLabel != null) {
			columnValue = arrayList.get(currentRow - 1).get(columnLabel).getLongValue();
		}
		return columnValue;
	}

	public float getFloat(String columnLabel) throws SQLException {
		float columnValue = 0;
		if (columnLabel != null) {
			columnValue = (Float) arrayList.get(currentRow).get(columnLabel).getValue();
		}
		return columnValue;
	}

	public double getDouble(String columnLabel) throws SQLException {
		double columnValue = 0;
		if (columnLabel != null) {
			columnValue = arrayList.get(currentRow).get(columnLabel).getDoubleValue();
		}
		return columnValue;
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getBytes(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(String columnLabel) throws SQLException {
		System.out.println("Method: getDate: String");
		Date columnValue = null;
		String dateStr = null;
		if (columnLabel != null) {
			if (currentRow == 0) {
				dateStr = arrayList.get(currentRow).get(columnLabel).getStringValue();
			} else {
				dateStr = arrayList.get(currentRow - 1).get(columnLabel).getStringValue();
			}
			
			columnValue = Date.valueOf(dateStr);
			System.out.println(columnValue);
		}
		return columnValue;
	}

	public Time getTime(String columnLabel) throws SQLException {
		System.out.println("Method: getTime: String");
		Time columnValue = null;
		String timeStr = null;
		if (columnLabel != null) {
			if (currentRow == 0) {
				timeStr = arrayList.get(currentRow).get(columnLabel).getStringValue();
			} else {
				timeStr = arrayList.get(currentRow - 1).get(columnLabel).getStringValue();
			}
			
			columnValue = Time.valueOf(timeStr);
			System.out.println(columnValue);
		}
		return columnValue;
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		System.out.println("Method: getTimestamp: String");
		Timestamp columnValue = null;
		String timestampStr = null;
		if (columnLabel != null) {
			if (currentRow == 0) {
				timestampStr = arrayList.get(currentRow).get(columnLabel).getStringValue();
			} else {
				timestampStr = arrayList.get(currentRow - 1).get(columnLabel).getStringValue();
			}
			
			columnValue = Timestamp.valueOf(timestampStr);
			System.out.println(columnValue);
		}
		return columnValue;
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigQueryResultSetMetaData getMetaData() throws SQLException {
		return new BigQueryResultSetMetaData(tableResult);
	}

	public Object getObject(int columnIndex) throws SQLException {
		Object columnValue = null;
		System.out.println("Method: getObject");
		int columnType = getMetaData().getColumnType(columnIndex);

		switch (columnType) {
		case Types.INTEGER: {
			return getLong(columnIndex);
		}
		case Types.DATE: {
			return getDate(columnIndex);
		}
		case Types.FLOAT: {
			return getBigDecimal(columnIndex);
		}
		case Types.BOOLEAN: {
			return getBoolean(columnIndex);
		}
		case Types.TIMESTAMP: {
			return getTimestamp(columnIndex);
		}
		default:
			if (currentRow == 0) {
				columnValue = arrayList.get(currentRow).get(columnIndex - 1).getValue();
			} else {
				columnValue = arrayList.get(currentRow - 1).get(columnIndex - 1).getValue();
			}
			
		}
		return columnValue;
	}

	public Object getObject(String columnLabel) throws SQLException {
		Object columnValue = null;
		if (columnLabel != null) {
			if (currentRow == 0) {
				columnValue = arrayList.get(currentRow).get(columnLabel).getValue();
			} else {
				columnValue = arrayList.get(currentRow - 1).get(columnLabel).getValue();
			}
			
		}
		return columnValue;
	}

	public int findColumn(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		BigDecimal columnValue = null;
		System.out.println("Method: getBigDecimal");
		String floatValue = null;
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				floatValue = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				floatValue = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			columnValue = new BigDecimal(floatValue);
		}
		return columnValue;
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean absolute(int row) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean relative(int rows) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setFetchDirection(int direction) throws SQLException {
		// TODO Auto-generated method stub

	}

	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setFetchSize(int rows) throws SQLException {
		// TODO Auto-generated method stub

	}

	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateNull(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateString(int columnIndex, String x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNull(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateByte(String columnLabel, byte x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateShort(String columnLabel, short x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateInt(String columnLabel, int x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateFloat(String columnLabel, float x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateString(String columnLabel, String x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateDate(String columnLabel, Date x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateTime(String columnLabel, Time x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateObject(String columnLabel, Object x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Ref getRef(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob getBlob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob getClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Array getArray(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Ref getRef(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob getBlob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob getClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Array getArray(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		Timestamp columnValue = null;
		String timestampStr = null;
		System.out.println("Method: getTimestamp");
		if (columnIndex <= columnCount) {
			if (currentRow == 0) {
				timestampStr = arrayList.get(currentRow).get(columnIndex - 1).getStringValue();
			} else {
				timestampStr = arrayList.get(currentRow - 1).get(columnIndex - 1).getStringValue();
			}
			System.out.println(timestampStr);
			try {
				double epochTimeWithFraction = Double.parseDouble(timestampStr);
                long epochTimeSeconds = (long) epochTimeWithFraction;
                int nanoSeconds = (int) ((epochTimeWithFraction - epochTimeSeconds) * 1_000_000_000);
                Instant instant = Instant.ofEpochSecond(epochTimeSeconds, nanoSeconds);
                columnValue = Timestamp.from(instant);
	        } catch (NumberFormatException e) {
	            columnValue = Timestamp.valueOf(LocalDateTime.parse(timestampStr, DateTimeFormatter.ISO_DATE_TIME));
	        }
		}
		return columnValue;
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getURL(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getURL(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateRef(String columnLabel, Ref x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateClob(String columnLabel, Clob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public RowId getRowId(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateNString(int columnIndex, String nString) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNString(String columnLabel, String nString) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub

	}

	public NClob getNClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub

	}

	public String getNString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
