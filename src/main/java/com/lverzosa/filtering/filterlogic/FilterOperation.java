package com.lverzosa.filtering.filterlogic;

import com.lverzosa.filtering.data.EmployeeColumn;
import com.lverzosa.filtering.data.Filter;

import java.util.Date;

/**
 * Enumeration of filtering operations that can occur on data.
 */
public enum FilterOperation {
    EQUAL_TO("=", new FilterMatchFunction() {
        public boolean isMatch(Object matchValue, Object columnValue) {
            return columnValue.equals(matchValue);
        }
    }),
    NOT_EQUAL_TO("!=", new FilterMatchFunction() {
        public boolean isMatch(Object matchValue, Object columnValue) {
            return !columnValue.equals(matchValue);
        }
    }),
    GREATER_THAN(">", new FilterMatchFunction<Integer>() {
        public boolean isMatch(Integer matchValue, Integer columnValue) {
            return columnValue > matchValue;
        }
    }),
    GREATER_THAN_OR_EQUAL_TO(">=", new FilterMatchFunction<Integer>() {
        public boolean isMatch(Integer matchValue, Integer columnValue) {
            return columnValue >= matchValue;
        }
    }),
    LESS_THAN("<", new FilterMatchFunction<Integer>() {
        public boolean isMatch(Integer matchValue, Integer columnValue) {
            return columnValue < matchValue;
        }
    }),
    LESS_THAN_OR_EQUAL_TO("<=", new FilterMatchFunction<Integer>() {
        public boolean isMatch(Integer matchValue, Integer columnValue) {
            return columnValue <= matchValue;
        }
    }),
    CONTAINS("contains", new FilterMatchFunction<String>() {
        public boolean isMatch(String matchValue, String columnValue) {
            return columnValue.contains(matchValue);
        }
    }),
    DOES_NOT_CONTAIN("not_contain", new FilterMatchFunction<String>() {
        public boolean isMatch(String matchValue, String columnValue) {
            return !columnValue.contains(matchValue);
        }
    }),
    STARTS_WITH("starts_with", new FilterMatchFunction<String>() {
        public boolean isMatch(String matchValue, String columnValue) {
            return columnValue.startsWith(matchValue);
        }
    }),
    ENDS_WITH("ends_with", new FilterMatchFunction<String>() {
        public boolean isMatch(String matchValue, String columnValue) {
            return columnValue.endsWith(matchValue);
        }
    }),
    AFTER(">", new FilterMatchFunction<Date>() {
        public boolean isMatch(Date matchValue, Date columnValue) {
            return columnValue.after(matchValue);
        }
    }),
    AFTER_OR_EQUAL_TO(">=", new FilterMatchFunction<Date>() {
        public boolean isMatch(Date matchValue, Date columnValue) {
            return columnValue.after(matchValue) || columnValue.equals(matchValue);
        }
    }),
    BEFORE("<", new FilterMatchFunction<Date>() {
        public boolean isMatch(Date matchValue, Date columnValue) {
            return columnValue.before(matchValue);
        }
    }),
    BEFORE_OR_EQUAL_TO("<=", new FilterMatchFunction<Date>() {
        public boolean isMatch(Date matchValue, Date columnValue) {
            return columnValue.before(matchValue) || columnValue.equals(matchValue);
        }
    });

    private final FilterMatchFunction matchFunction;
    private final String operatorName;

    FilterOperation(String operatorName, FilterMatchFunction matchFunction) {
        this.operatorName = operatorName;
        this.matchFunction = matchFunction;
    }

    /**
     * Returns the name of the operator as defined in Filter object.
     *
     * @return name of operator.
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * Returns true if values match according to operator.
     *
     * @param matchValue  match value.
     * @param columnValue column value.
     * @return true if match
     */
    @SuppressWarnings("unchecked")
    public boolean match(Object matchValue, Object columnValue) {
        // not sure how to handle null so if either value is null then return false
        return columnValue != null && matchValue != null && this.matchFunction.isMatch(matchValue, columnValue);
    }

    /**
     * Finds the operator for the given filter.
     *
     * @param filter the filter to process.
     * @return the operator of the filter.
     */
    public static FilterOperation findOperator(Filter filter) {
        return EmployeeColumn.valueOf(filter.getFieldId()).getColumnType().findFilterOperation(filter.getOperator());
    }
}
