package com.lverzosa.filtering.data;

import com.google.common.collect.ImmutableMap;
import com.lverzosa.filtering.util.DateUtil;

import java.util.Date;
import java.util.Map;

/**
 * Employee data.
 */
public class Employee {
    private final ImmutableMap<EmployeeColumn, String> columns;

    public Employee(Map<EmployeeColumn, String> columns) {
        this.columns = ImmutableMap.copyOf(columns);
    }

    public Object getValue(EmployeeColumn column) {
        return column.getColumnValue(this);
    }

    public String getStringValue(EmployeeColumn key) {
        return columns.get(key);
    }

    public Integer getIntegerValue(EmployeeColumn key) {
        String value = columns.get(key);
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value);
    }

    public Date getDateValue(EmployeeColumn key) {
        String value = columns.get(key);
        if (value == null) {
            return null;
        }
        return DateUtil.convert(value);
    }
}
