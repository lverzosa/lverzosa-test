package com.lverzosa.filtering.filterlogic;

import com.google.common.collect.ImmutableMap;

/**
 * Column types and valid operations for data input.
 */
public enum ColumnType {
    INTEGER(FilterOperation.EQUAL_TO, FilterOperation.NOT_EQUAL_TO, FilterOperation.GREATER_THAN, FilterOperation.GREATER_THAN_OR_EQUAL_TO, FilterOperation.LESS_THAN, FilterOperation.LESS_THAN_OR_EQUAL_TO),
    STRING(FilterOperation.EQUAL_TO, FilterOperation.NOT_EQUAL_TO, FilterOperation.CONTAINS, FilterOperation.DOES_NOT_CONTAIN, FilterOperation.STARTS_WITH, FilterOperation.ENDS_WITH),
    DATE(FilterOperation.EQUAL_TO, FilterOperation.NOT_EQUAL_TO, FilterOperation.AFTER, FilterOperation.AFTER_OR_EQUAL_TO, FilterOperation.BEFORE, FilterOperation.BEFORE_OR_EQUAL_TO);

    private final ImmutableMap<String, FilterOperation> validOperations;

    ColumnType(FilterOperation... filterOperations) {
        ImmutableMap.Builder<String, FilterOperation> builder = new ImmutableMap.Builder<String, FilterOperation>();

        for (FilterOperation filterOperation : filterOperations) {
            builder.put(filterOperation.getOperatorName(), filterOperation);
        }

        this.validOperations = builder.build();
    }

    /**
     * Given the operator name return the filter operation for this type.
     *
     * @param operatorName name in Filter object
     * @return the operation for the filter
     */
    public FilterOperation findFilterOperation(String operatorName) {
        return this.validOperations.get(operatorName);
    }
}
