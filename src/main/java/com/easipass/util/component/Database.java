package com.easipass.util.component;

import com.easipass.util.config.BaseConfig;
import com.easipass.util.entity.AbstractPO;
import com.easipass.util.entity.po.Column;
import com.easipass.util.entity.po.Table;
import com.zj0724.common.component.jdbc.AccessDatabaseJdbc;
import com.zj0724.common.entity.QueryResult;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.*;
import com.zj0724.common.entity.Query;
import com.zj0724.common.exception.ErrorException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 数据库
 *
 * @author ZJ
 * */
public final class Database<T extends AbstractPO> {

    private final Class<T> c;

    private final String tableName;

    private static final List<Database<? extends AbstractPO>> DATABASE_LIST = new ArrayList<>();

    static {
        if (!BaseConfig.DATABASE_FILE.exists()) {
            AccessDatabaseJdbc.create(com.healthmarketscience.jackcess.Database.FileFormat.V2016, BaseConfig.DATABASE_FILE.getAbsolutePath()).close();
        }
    }

    private Database(Class<T> c) {
        this.c = c;

        Table table = c.getAnnotation(Table.class);
        if (table == null) {
            throw new ErrorException(c.getName() + "未配置@Table");
        }
        tableName = table.name();

        List<Field> fields = ClassUtil.getAllFields(this.c);
        Map<String, AccessDatabaseJdbc.FieldType> fieldTypeMap = new LinkedHashMap<>();
        for (Field field : fields) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation == null) {
                throw new ErrorException(field.getName() + "未配置@Column");
            }
            fieldTypeMap.put(annotation.name(), annotation.type());
        }

        AccessDatabaseJdbc accessDatabaseJdbc = new AccessDatabaseJdbc(BaseConfig.DATABASE_FILE.getAbsolutePath());
        try {
            accessDatabaseJdbc.reloadTable(tableName, fieldTypeMap);
        } finally {
            accessDatabaseJdbc.close();
        }
    }

    /**
     * 查询
     *
     * @param query query
     *
     * @return QueryResult
     * */
    public QueryResult<T> query(Query query) {
        QueryResult<T> queryResult = new QueryResult<>();
        AccessDatabaseJdbc accessDatabaseJdbc = new AccessDatabaseJdbc(BaseConfig.DATABASE_FILE.getAbsolutePath());
        try {
            QueryResult<Map<String, Object>> data = accessDatabaseJdbc.query(this.tableName, query);
            queryResult.setCount(data.getCount());
            List<T> tList = new ArrayList<>();
            List<Map<String, Object>> mapList = data.getData();
            for (Map<String, Object> map : mapList) {
                map = mapToField(map, this.c);
                tList.add(MapUtil.parseObject(map, this.c));
            }
            queryResult.setData(tList);
            return queryResult;
        } finally {
            accessDatabaseJdbc.close();
        }
    }

    public QueryResult<T> query() {
        return query(null);
    }

    /**
     * 保存
     *
     * @param t t
     * */
    public void save(T t) {
        if (t == null) {
            return;
        }
        AccessDatabaseJdbc accessDatabaseJdbc = new AccessDatabaseJdbc(BaseConfig.DATABASE_FILE.getAbsolutePath());
        try {
            if (t.getId() == null) {
                Map<String, Object> map = mapToColumn(ObjectUtil.parseMap(t), c);
                map.put("ID", getNextId());
                String sql = SqlUtil.parseInsertSql(map, c.getAnnotation(Table.class).name());
                accessDatabaseJdbc.execute(sql);
            } else {
                update(t.getId(), t);
            }
        } finally {
            accessDatabaseJdbc.close();
        }
    }

    /**
     * 通过id修改
     *
     * @param id id
     * @param t t
     * */
    public void update(Long id, T t) {
        if (id == null) {
            throw new InfoException("id不能为空");
        }
        List<Field> fields = ClassUtil.getFields(t.getClass());
        String sql = "UPDATE " + tableName + " SET ";
        for (Field field : fields) {
            String value;
            Object o;
            try {
                field.setAccessible(true);
                o = field.get(t);
            } catch (Exception e) {
                throw new InfoException(e.getMessage());
            }
            if (o == null) {
                value = "NULL";
            } else {
                value = "'"+ o + "'";
            }
            sql = StringUtil.append(sql, field.getAnnotation(Column.class).name(), " = ", value, ", ");
        }
        sql = sql.substring(0, sql.length() - 2);
        sql = StringUtil.append(sql, " WHERE ID = " + id);

        AccessDatabaseJdbc accessDatabaseJdbc = new AccessDatabaseJdbc(BaseConfig.DATABASE_FILE.getAbsolutePath());
        try {
            accessDatabaseJdbc.execute(sql);
        } finally {
            accessDatabaseJdbc.close();
        }
    }

    /**
     * 通过id删除
     *
     * @param id id
     * */
    public void deleteById(Long id) {
        if (id == null) {
            throw new InfoException("id不能为空");
        }
        AccessDatabaseJdbc accessDatabaseJdbc = new AccessDatabaseJdbc(BaseConfig.DATABASE_FILE.getAbsolutePath());
        try {
            accessDatabaseJdbc.execute("DELETE FROM " + tableName + " WHERE ID = " + id);
        } finally {
            accessDatabaseJdbc.close();
        }
    }

    /**
     * 获取下一个id
     *
     * @return 下一个id
     * */
    private Long getNextId() {
        AccessDatabaseJdbc accessDatabaseJdbc = new AccessDatabaseJdbc(BaseConfig.DATABASE_FILE.getAbsolutePath());
        try {
            List<Map<String, Object>> list = accessDatabaseJdbc.queryBySql("SELECT * FROM " + tableName + " ORDER BY ID DESC");
            if (list.size() == 0) {
                return 1L;
            }
            Map<String, Object> map = list.get(0);
            Object id = map.get("ID");
            if (id == null) {
                throw new InfoException("id 为 null");
            }
            return Long.parseLong(id.toString()) + 1;
        } finally {
            accessDatabaseJdbc.close();
        }
    }

    @SuppressWarnings("unchecked")
    public synchronized static <E extends AbstractPO> Database<E> getDatabase(Class<E> c) {
        for (Database<?> database : DATABASE_LIST) {
            if (database.c == c) {
                return (Database<E>) database;
            }
        }
        Database<E> database = new Database<>(c);
        DATABASE_LIST.add(database);
        System.out.println("新的database: " + c.getName());
        return database;
    }

    /**
     * 将map的key转换成column注解的值
     *
     * @param map map
     * @param c c
     *
     * @return 新的map
     * */
    private static Map<String, Object> mapToColumn(Map<?, ?> map, Class<? extends AbstractPO> c) {
        Map<String, Object> result = new HashMap<>();
        Set<? extends Map.Entry<?, ?>> entries = map.entrySet();
        List<Field> allFields = ClassUtil.getAllFields(c);
        for (Map.Entry<?, ?> entry : entries) {
            String newKey = null;
            for (Field field : allFields) {
                if (field.getName().equals(entry.getKey())) {
                    newKey = field.getAnnotation(Column.class).name();
                    break;
                }
            }
            if (newKey == null) {
                throw new ErrorException("map转换出错：" + entry.getKey());
            }
            result.put(newKey, entry.getValue());
        }
        return result;
    }

    /**
     * 将map的key转换成类的值
     *
     * @param map map
     * @param c c
     *
     * @return 新的map
     * */
    private static Map<String, Object> mapToField(Map<String, Object> map, Class<? extends AbstractPO> c) {
        Map<String, Object> result = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        List<Field> allFields = ClassUtil.getAllFields(c);
        for (Map.Entry<String, Object> entry : entries) {
            String newKey = null;
            for (Field field : allFields) {
                if (field.getAnnotation(Column.class).name().equals(entry.getKey())) {
                    newKey = field.getName();
                    break;
                }
            }
            if (newKey == null) {
                throw new ErrorException("map转换出错：" + entry.getKey());
            }
            result.put(newKey, entry.getValue());
        }
        return result;
    }

}