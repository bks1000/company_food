package com.june.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.lob.LobHandler;

import com.june.dto.PageDTO;
import com.june.dto.ParamDTO;
import com.june.enums.ProParamType;

public class DataBaseUtils {

	private static Logger logger = Logger.getLogger(DataBaseUtils.class);

	private static JdbcTemplate jdbcTemplate = null;
	private static NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
	private static LobHandler lobHandler = null;
	
	public static JdbcTemplate getJdbcTemplate() {
		Lock lock = new ReentrantLock();
		try {
			lock.lock();
			if (jdbcTemplate == null) {
				try {
					jdbcTemplate = (JdbcTemplate) BeanUtils.getInstance().getBean("jdbcTemplate",JdbcTemplate.class);
				} catch (BeansException e) {
				}

				if (jdbcTemplate == null) {
					DataSource ds = (DataSource) BeanUtils.getInstance().getBean("dataSource", DataSource.class);
					if (ds == null) {
						ds = (DataSource) BeanUtils.getInstance().getBean("dataSource",DataSource.class);
					}
					jdbcTemplate = new JdbcTemplate(ds);
				}
			}
		} finally {
			lock.unlock();
		}
		return jdbcTemplate;
	}

	public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		Lock lock = new ReentrantLock();
		try {
			lock.lock();
			if (namedParameterJdbcTemplate == null) {
				if (jdbcTemplate == null) {
					jdbcTemplate = getJdbcTemplate();
				}
				namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			}
		} finally {
			lock.unlock();
		}
		return namedParameterJdbcTemplate;
	}



	protected static BeanPropertySqlParameterSource paramBeanMapper( Object object) {
		return new BeanPropertySqlParameterSource(object);
	}

	protected static MapSqlParameterSource paramMapMapper(Map<String,Object> object) {
		return new MapSqlParameterSource(object);
	}

	@SuppressWarnings("unchecked")
	protected static AbstractSqlParameterSource paramMapper(Object object) {
		if ((object instanceof Map)) {
			return paramMapMapper((Map<String,Object>) object);
		}
		return paramBeanMapper(object);
	}

	protected static AbstractSqlParameterSource[] paramListMapper(List<Map<String, Object>> list) {
		if (list != null) {
			AbstractSqlParameterSource[] sources = new AbstractSqlParameterSource[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Object item = list.get(i);
				sources[i] = paramMapper(item);
			}
			return sources;
		}
		return null;
	}

	protected static <T> BeanPropertyRowMapper<T> resultBeanMapper( Class<T> clazz) {
		return BeanPropertyRowMapper.newInstance(clazz);
	}

	public static String[] getColumnFields(String sql, Object... args){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		List<String> list = new ArrayList<String>();

		try {
			con = DataSourceUtils.getConnection(getJdbcTemplate().getDataSource());
			pstmt = con.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					pstmt.setObject(i + 1, args[i]);
				}
			}
			rs = pstmt.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String name = meta.getColumnName(i);
				list.add(name);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			DataSourceUtils.releaseConnection(con, getJdbcTemplate().getDataSource());

		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static int update(String sql, Object... args) {
		return getJdbcTemplate().update(sql, args);
	}
	
	public static int update(String sql, Map<String, Object> paramMap) {
		return getNamedParameterJdbcTemplate().update(sql, paramMapMapper(paramMap));
	}
	
	public static <T> int update(String sql,Class<T> clz, T paramBean) {
		return getNamedParameterJdbcTemplate().update(sql,paramBeanMapper(paramBean));
	}

	public static int[] batchUpdate(String... sql) {
		return getJdbcTemplate().batchUpdate(sql);
	}

	public static int[] batchUpdate(String sql, List<Map<String, Object>> paramlist) {
		return getNamedParameterJdbcTemplate().batchUpdate(sql,paramListMapper(paramlist));
	}

	public static List<Map<String, Object>> queryForList(String sql, Object... args){
	    return DataBaseUtils.getJdbcTemplate().queryForList(sql, args);
	}
	
	public static List<Map<String, Object>> queryForList(String sql){
	    return DataBaseUtils.getJdbcTemplate().queryForList(sql);
	}
	public static List<Map<String, Object>> queryForList(String sql, Map<String, Object> paramMap){
	    return getNamedParameterJdbcTemplate().queryForList(sql, paramMapMapper(paramMap));
  }

	public static Map<String, Object> queryForMap(String sql, Object... args) {
		List<Map<String, Object>> list = queryForList(sql, args);
		if ((list != null) && (list.size() > 0)) {
			return (Map<String, Object>) list.get(0);
		}
		return null;
	}

	public static Map<String, Object> queryForMap(String sql, Map<String,Object> args) {
		return getNamedParameterJdbcTemplate().queryForMap(sql, paramMapMapper(args));
	}

	public static <T> T queryForScalar(String sql, final Class<T> clz, Object... args) {
		return getJdbcTemplate().queryForObject(sql,clz, args);
	}
	
	public static <T> T queryForScalar(String sql, final Class<T> clz, Map<String, Object> paramMap) {
		return getNamedParameterJdbcTemplate().queryForObject(sql, paramMapMapper(paramMap), clz);
	}

    public static <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        return getJdbcTemplate().query(sql, rowMapper);
    }

    public <T> T execute(String sql, PreparedStatementCallback<T> action) {
        return getJdbcTemplate().execute(sql, action);
    }

	public static <T> T queryForBean(String sql, Class<T> clz, Object... objects) {
		List<T> list = queryForListBean(sql, clz, objects);
		if ((list != null) && (list.size() > 0)) {
			return list.get(0);
		}
		return null;
	}
	
	public static <T> List<T> queryForListBean(String sql, final Class<T> clz, Object... args){
		return getJdbcTemplate().query(sql, resultBeanMapper(clz), args);
	}

	/**
	 * 分页查询*/
	public static PageDTO queryForPage(String sql, Map<String, Object> paramMap){
		PageDTO page = new PageDTO();
		try {
			int pageSize = paramMap.get("pagesize")==null?10:Integer.parseInt(paramMap.get("pagesize").toString());
			int currentPage = paramMap.get("currentpage")==null?1:Integer.parseInt(paramMap.get("currentpage").toString());
			int startIndex = (currentPage-1)*pageSize;
			int endIndex = currentPage*pageSize;

			String pageSql = "select table__.* from (select rownum as rownum_,table_.* from ("+sql+") table_) table__ where table__.rownum_ > "+startIndex+" and table__.rownum_ <= "+endIndex;

			List<Map<String, Object>> data = queryForList(pageSql, paramMap);
			page.setData(data);

			pageSql = "select count(1) from ("+sql+")";
			int count = queryForScalar(pageSql, Integer.class, paramMap);
			page.setTotal(count);


		} catch (Exception e) {
			logger.error(e.getMessage());

		}

		return page;
	}

	/**
	 * 分页查询*/
	public static PageDTO queryForPage(String sql, Map<String, Object> paramMap, Object... args) {
		PageDTO page = new PageDTO();
		try {
			int pageSize = paramMap.get("pagesize") == null ? 10 : Integer.parseInt(paramMap.get("pagesize").toString());
			int currentPage = paramMap.get("currentpage") == null ? 1 : Integer.parseInt(paramMap.get("currentpage").toString());
			int startIndex = (currentPage - 1) * pageSize;
			int endIndex = currentPage * pageSize;

			String pageSql = "select table__.* from (select rownum as rownum_,table_.* from (" + sql + ") table_) table__ where table__.rownum_ > " + startIndex + " and table__.rownum_ <= " + endIndex;

			List<Map<String, Object>> data = queryForList(pageSql, args);
			page.setData(data);

			pageSql = "select count(1) from (" + sql + ")";
			int count = queryForScalar(pageSql, Integer.class, args);
			page.setTotal(count);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return page;
	}

    public static List<Map<String, Object>> exProcedureGetData(String proName, final List<ParamDTO> list) {
        String sql = "{call " + proName + "(";

        for (int k = 0; k < list.size(); k++) {
            sql += " ?,";
        }
        sql += " ?)}";
        return DataBaseUtils.getJdbcTemplate().execute(sql,
                new CallableStatementCallback<List<Map<String, Object>>>() {

					public List<Map<String, Object>> doInCallableStatement(CallableStatement cs) {
						List<Map<String, Object>> resultsList = new ArrayList<Map<String, Object>>();
						try {
							for (int j = 0; j < list.size(); j++) {
                                ProParamType type = list.get(j).getParamType();
                                if (type == ProParamType.STRING) {
                                    cs.setString(j + 1, list.get(j).getParamValue().toString());

                                } else if (type == ProParamType.INTEGER) {
                                    cs.setInt(j + 1, Integer.valueOf(list.get(j).getParamValue().toString()));
                                } else if (type == ProParamType.LONG) {
                                    cs.setLong(j + 1, Long.valueOf(list.get(j).getParamValue().toString()));
                                } else if (type == ProParamType.FLOAT) {
                                    cs.setFloat(j + 1, Float.valueOf(list.get(j).getParamValue().toString()));
                                } else if (type == ProParamType.DOUBLE) {
                                    cs.setDouble(j + 1, Double.valueOf(list.get(j).getParamValue().toString()));
                                } else if (type == ProParamType.BOOLEAN) {
                                    cs.setBoolean(j + 1, Boolean.valueOf(list.get(j).getParamValue().toString()));
								} else if(type== ProParamType.BLOB) {
								    byte[] bytes=list.get(j).getParamBtye();
									InputStream is = new ByteArrayInputStream(bytes);
                                	cs.setBlob(j + 1,is);
								}
								else{
                                    cs.setString(j + 1, list.get(j).getParamValue().toString());
                                }
                            }
							cs.registerOutParameter(list.size() + 1, OracleTypes.CURSOR);
							cs.execute();

							ResultSet rs = (ResultSet) cs.getObject(list.size() + 1);
							if(rs!=null){
								ResultSetMetaData data = rs.getMetaData();
								int ColumnCount = data.getColumnCount();

								while (rs.next()) {
									Map<String, Object> rowMap = new LinkedHashMap<String, Object>();
									for (int i = 1; i <= ColumnCount; i++) {
										String colName = data.getColumnName(i);
										Object colValue = rs.getObject(colName);
                                        String clsName = colValue != null ? colValue.getClass().getName() : "null";
                                        if (("oracle.sql.CLOB").equals(clsName)) {
                                            colValue = oracleClob2Str((Clob) colValue);
                                        }
                                        //System.out.println(colName + "=======" + clsName + "=========" + colValue + "");//===================================
                                        rowMap.put(colName, colValue == null ? "" : colValue.toString());
									}
									resultsList.add(rowMap);
								}
								rs.close();
							}
						
						} catch (Exception e) {
							try {
								throw e;
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						return resultsList;
					}
				});
	}

    public static String oracleClob2Str(Clob clob) throws Exception {
        return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
    }

    public static LobHandler getLobHandler() {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            if (lobHandler == null) {
                lobHandler = (LobHandler) BeanUtils.getInstance().getBean("oracleLobHandler", LobHandler.class);
            }
        } finally {
            lock.unlock();
        }
        return lobHandler;
    }

	/**
	 * 执行存储过程不返回数据集
	 */
    public static void exProcedureNonData(String proName, final List<ParamDTO> list) {
        String sql = "{call " + proName;
        if (list.size() > 0) {
            sql += "(?";

            for (int k = 1; k < list.size(); k++) {

                sql += ",?";
            }
            sql += ")";
        }
        sql += "}";
        DataBaseUtils.getJdbcTemplate().execute(sql,
				new CallableStatementCallback<List<Map<String, Object>>>() {

					public List<Map<String, Object>> doInCallableStatement(CallableStatement cs) {
						List<Map<String, Object>> resultsList = new ArrayList<Map<String, Object>>();
						try {
							for (int j = 0; j < list.size(); j++) {
                                ProParamType type = list.get(j).getParamType();
                                Object o=list.get(j);
                                if (type == ProParamType.STRING) {
                                    cs.setString(j + 1, list.get(j).getParamValue().toString());
                                } else if (type == ProParamType.INTEGER) {
                                    cs.setInt(j + 1, Integer.valueOf(list.get(j).getParamValue().toString()));
                                } else if (type == ProParamType.LONG) {
                                    cs.setLong(j + 1, Long.valueOf(list.get(j).getParamValue().toString()));
                                } else if (type == ProParamType.FLOAT) {
                                    cs.setFloat(j + 1, Float.valueOf(list.get(j).getParamValue().toString()));
                                } else if (type == ProParamType.DOUBLE) {
                                    cs.setDouble(j + 1, Double.valueOf(list.get(j).getParamValue().toString()));
                                } else if (type == ProParamType.BOOLEAN) {
                                    cs.setBoolean(j + 1, Boolean.valueOf(list.get(j).getParamValue().toString()));
                                } else if(type== ProParamType.BLOB) {
                                	ByteArrayOutputStream byt=new ByteArrayOutputStream();
								    ObjectOutputStream obj=new ObjectOutputStream(byt);
								    obj.writeObject(list.get(j).getParamValue());
								    byte[] bytes=byt.toByteArray();
									InputStream is = new ByteArrayInputStream(bytes);
                                	cs.setBlob(j + 1,is);
                                }
                                else{
                                    cs.setString(j + 1, list.get(j).getParamValue().toString());
                                }
							}

							cs.execute();

						} catch (Exception e) {
							try {
								throw e;
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						return resultsList;
					}
				});
	}

	/**
	 * 查看SQL
	 * @param sql
	 * @param map
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getSql(String sql,Map<String, Object> map) {
		for ( Map.Entry<String, Object> entry : map.entrySet()) {
			sql = Matcher.quoteReplacement(sql).replaceAll(":"+entry.getKey().toString(), entry.getValue().toString());
		}
		return sql;
	}
	
	public static String getSql(String sql,Object... params) {
		int j = 0;
		for(int i = 0;i < sql.length();i++){
			 if(sql.charAt(i) == '?'){
				 sql = sql.replaceFirst("\\?", "'" + String.valueOf(params[j++]) + "'");//替换结束后再给本身赋值
			 }
		}
		logger.info(sql);
		return sql;
	}
}
