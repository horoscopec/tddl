package com.taobao.tddl.executor.cursor.impl;

import java.util.ArrayList;
import java.util.List;

import com.taobao.tddl.common.exception.TddlException;
import com.taobao.tddl.executor.common.ExecutionContext;
import com.taobao.tddl.executor.cursor.ICursorMeta;
import com.taobao.tddl.executor.cursor.ResultCursor;
import com.taobao.tddl.executor.rowset.ArrayRowSet;
import com.taobao.tddl.executor.rowset.IRowSet;
import com.taobao.tddl.optimizer.config.table.ColumnMeta;
import com.taobao.tddl.optimizer.core.expression.IColumn;
import com.taobao.tddl.optimizer.core.expression.ISelectable.DATA_TYPE;
import com.taobao.tddl.optimizer.core.expression.bean.Column;

/**
 * 用于返回执行计划
 * 
 * @author mengshi.sunmengshi 2013-12-3 下午5:41:36
 * @since 5.1.0
 */
public class QueryPlanResultCursor extends ResultCursor {

    static List<Object>     columns = new ArrayList();
    static List<ColumnMeta> cms     = new ArrayList();
    static {
        IColumn c = new Column();
        c.setColumnName("EXPLAIN");
        c.setDataType(DATA_TYPE.STRING_VAL);
        columns.add(c);

        ColumnMeta cm = new ColumnMeta(null, "EXPLAIN", DATA_TYPE.STRING_VAL, null, false);
        cms.add(cm);
    }
    List<IRowSet>           rows    = new ArrayList();

    public QueryPlanResultCursor(String queryPlan, ExecutionContext executionContext){
        super(null, executionContext);

        ICursorMeta meta = CursorMetaImp.buildNew(cms);
        IRowSet row = new ArrayRowSet(meta, new String[] { queryPlan });
        rows.add(row);
    }

    @Override
    public List<Object> getOriginalSelectColumns() {
        return columns;
    }

    @Override
    public void setOriginalSelectColumns(List<Object> originalSelectColumns) {
        return;
    }

    @Override
    public int getTotalCount() {
        return 1;
    }

    int index = 0;

    @Override
    public IRowSet next() throws TddlException {
        if (index > 0) {
            return null;
        }

        return rows.get(index++);
    }

    @Override
    public void beforeFirst() throws TddlException {
        index = 0;
        return;
    }

    @Override
    public List<TddlException> close(List<TddlException> exceptions) {
        if (exceptions == null) {
            exceptions = new ArrayList();
        }
        return exceptions;
    }

}
