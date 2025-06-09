package com.custom.dlp.cmmn.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * 클래스명: CustomSqlLoggingInterceptor
 * 설명: SQL 로깅용 인터셉터
 * </pre>
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Log4j2
public class CustomSqlLoggingInterceptor implements Interceptor {

	@Value("${sql-logging-before-binding}")
	private boolean sqlLoggingBeforeBinding;
	
	@Value("${sql-logging-binding-params}")
	private boolean sqlLoggingBindingParams;
	
	@Value("${sql-logging-after-binding}")
	private boolean sqlLoggingAfterBinding;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		String originalSql = statementHandler.getBoundSql().getSql();
		Object paramterObject = statementHandler.getBoundSql().getParameterObject();
		
		List<Object> paramList = getParamList(statementHandler.getBoundSql());
		String bindingSql = getBindingSql(originalSql, paramList);
		
		if (sqlLoggingBeforeBinding && log.isDebugEnabled()) { log.debug("바인딩 전 쿼리\n" + originalSql.replaceAll("\n(\t|(\s){4}){2}", "\n")); }
		if ((sqlLoggingBindingParams) && log.isDebugEnabled()) { log.debug("바인딩 파라미터\n" + (paramterObject == null ? "empty parameterObject" : paramterObject)); }
		if (sqlLoggingAfterBinding && log.isDebugEnabled()) { log.debug("바인딩 후 쿼리\n" + bindingSql.replaceAll("\n(\t|(\s){4}){2}", "\n")); }
		
		
		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Interceptor.super.plugin(target);
	}

	@Override
	public void setProperties(Properties properties) {
		Interceptor.super.setProperties(properties);
	}
	
	private static List<Object> getParamList(BoundSql boundSql) {
		List<Object> paramList = new ArrayList<>();
		
		Object paramterObject = boundSql.getParameterObject();
		
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		
		try {
			for (ParameterMapping parameterMapping : parameterMappings) {
				String properteyName = parameterMapping.getProperty();
				
				if (PropertyUtils.isReadable(paramterObject, properteyName)) { // DTO 객체인 경우 해당 필드값을 가져와서 리스트에 추가
					Object object = PropertyUtils.getProperty(paramterObject, properteyName);
					paramList.add(object);
				} else { // DTO 객체가 아닌 경우
					if (paramterObject instanceof Map) { // Map 형태인 경우 해당 필드값을 가져와서 리스트에 추가 
						Object object = ((Map<?, ?>) paramterObject).get(properteyName);
						paramList.add(object);
					} else { // 그 외의 경우 파라미터 자체를 리스트에 추가
						paramList.add(paramterObject);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return paramList;
	}

	public static String getBindingSql(String originalSql, List<Object> paramList) {
		StringBuilder sb = new StringBuilder();
		
		int questionMarkIndex; // ? 의 index
		int paramIndex = 0; // ? 체크 개수
		
		while ((questionMarkIndex = originalSql.indexOf("?")) >= 0) { // ? 가 있으면
			if (paramIndex == paramList.size()) { // ? 를 파라미터로 대체하는 것이 끝나면
				break;
			}
			
			// sb 에 ? 이전 부분을 추가
			sb.append(originalSql.substring(0, questionMarkIndex));
			
			// sb 에 ? 을 파라미터로 대체한 값을 추가
			if (paramList.get(paramIndex) instanceof String) {
				sb.append("'" + paramList.get(paramIndex)  + "'");
			} else if (paramList.get(paramIndex) instanceof Integer || paramList.get(paramIndex) instanceof Long) {
				sb.append(paramList.get(paramIndex));
			} else if (paramList.get(paramIndex) == null) {
				sb.append("null");
			} else {
				sb.append(paramList.get(paramIndex));
			}
			
			// originalSql 에서 ? 와 ? 이전 부분을 제거
			originalSql = originalSql.substring(questionMarkIndex + "?".length());
			
			paramIndex++;
		}
		
		// sb에 ? 이후 남은 originalSql 추가
		if (!"".equals(originalSql)) {
			sb.append(originalSql);
		}
		
		if (paramIndex == 0) {
			return originalSql;
		} else {
			return sb.toString();
		}
		
	}
}
