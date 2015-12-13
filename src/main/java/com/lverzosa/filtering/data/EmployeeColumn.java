package com.lverzosa.filtering.data;

import com.lverzosa.filtering.filterlogic.ColumnType;
import com.lverzosa.filtering.util.DateUtil;

/**
 * Employee data schema.
 */
public enum EmployeeColumn {
    employeeId(ColumnType.INTEGER),
    firstName(ColumnType.STRING),
    lastName(ColumnType.STRING),
    jobDescription(ColumnType.STRING),
    departmentName(ColumnType.STRING),
    managerId(ColumnType.INTEGER),
    dateHired(ColumnType.DATE),
    lastPasswordChange(ColumnType.DATE);

    private final ColumnType columnType;

    EmployeeColumn(ColumnType columnType) {
        this.columnType = columnType;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public Object getColumnValue(Employee employee) {
        switch (columnType) {
            case INTEGER:
                return employee.getIntegerValue(this);
            case STRING:
                return employee.getStringValue(this);
            case DATE:
                return employee.getDateValue(this);
            default:
                return null; // will not happen
        }
    }

    public Object getFilterValue(Filter filter) {
        switch (columnType) {
            case INTEGER:
                return Integer.valueOf(filter.getValue());
            case STRING:
                return filter.getValue();
            case DATE:
                return DateUtil.convert(filter.getValue());
            default:
                return null; // will not happen
        }
    }
}
