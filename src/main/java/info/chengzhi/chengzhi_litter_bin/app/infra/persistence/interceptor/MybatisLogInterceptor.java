package info.chengzhi.chengzhi_litter_bin.app.infra.persistence.interceptor;

import info.chengzhi.chengzhi_litter_bin.app.domain.utils.ThreadLocalUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.*;

@Intercepts({
    @Signature(type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisLogInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisLogInterceptor.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String errMsg = "";
        String dltag = "_com_mysql_success";
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        final String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        Object returnValue = null;
        final long start = System.currentTimeMillis();
        try {
            returnValue = invocation.proceed();
        } catch (Exception ex) {
            dltag = "_com_mysql_failure";
            errMsg = ex.getMessage();
            LOGGER.error("sqlexception: {}", ex.getMessage());
            throw ex;
        } finally {
            try {
                final long end = System.currentTimeMillis();
                String sqlStatement;
                try {
                    sqlStatement =showSql(configuration, boundSql);
                } catch (Exception ex) {
                    LOGGER.warn("get sql statement error, errMsg: {}", ex.getMessage());
                    sqlStatement = boundSql.getSql();
                }
                Long procTime = end - start;
                LOGGER.info("trace_id={}||proc_time={}||sql_id={}||sql={}||returnValue={}",
                    ThreadLocalUtils.getTraceId(),
                    procTime,
                    sqlId,
                    sqlStatement,
                    returnValue);
            } catch (Exception ex) {
                LOGGER.error("log mybatis sql exception :{}", ex.getMessage());
            }
        }
        return returnValue;
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    private static String getParameterValue(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof String) {
            sb.append("'");
            sb.append(obj);
            sb.append("'");
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            sb.append("'");
            sb.append(formatter.format(obj));
            sb.append("'");
        } else if (obj != null) {
            sb.append(obj);
        } else {
            return null;
        }
        return sb.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
