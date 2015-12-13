package com.lverzosa.filtering;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lverzosa.filtering.data.Employee;
import com.lverzosa.filtering.data.EmployeeColumn;
import com.lverzosa.filtering.data.Filter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by lorenz on 12/13/15.
 */
public final class FilterAndOrUtil {
    private FilterAndOrUtil() {
    }

    /**
     * Method that filters employees by set of filters. Also includes or column where filters that match or column
     * are treated as or within that column. All other filters are treated as and.
     *
     * @param employees list of employees to filter.
     * @param filters   list of filters.
     * @param orColumns list of columns where filters are treated as or.
     * @return filtered list of employees.
     */
    public static List<Employee> filterEmployees(List<Employee> employees, List<Filter> filters, Set<EmployeeColumn> orColumns) {
        HashMap<EmployeeColumn, LinkedList<Filter>> orColumnFilterMap = Maps.newHashMap();
        LinkedList<Filter> andFilters = Lists.newLinkedList();

        // preprocess filters
        for (Filter filter : filters) {
            EmployeeColumn employeeColumn = EmployeeColumn.valueOf(filter.getFieldId());
            if (orColumns.contains(employeeColumn)) {
                // store in OR column hash map
                LinkedList<Filter> columnList = orColumnFilterMap.get(employeeColumn);
                if (columnList == null) {
                    columnList = Lists.newLinkedList();
                    orColumnFilterMap.put(employeeColumn, columnList);
                }
                columnList.add(filter);
            } else {
                // store in AND list
                andFilters.add(filter);
            }
        }
        List<Employee> filteredEmployees = Lists.newLinkedList();

        // process ANDs using the original logic
        for (Employee employee : FilterUtil.filterEmployees(employees, andFilters)) {
            if (employeeOrMatch(employee, orColumnFilterMap)) {
                filteredEmployees.add(employee);
            }
        }

        return filteredEmployees;
    }

    /**
     * Filters employee according to or filter map. Within each map value is treated as OR. Each map entry is an AND.
     *
     * @param employee          employee to check against.
     * @param orColumnFilterMap or filter map where or is within a value but and is between entries
     * @return true if employee passes filters.
     */
    private static boolean employeeOrMatch(Employee employee, HashMap<EmployeeColumn, LinkedList<Filter>> orColumnFilterMap) {
        // AND logic between columns
        for (LinkedList<Filter> filters : orColumnFilterMap.values()) {
            if (!employeeOrMatch(employee, filters)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Filters employee according to or filters. Returns true if any one filter matches employee.
     *
     * @param employee  employee to filter
     * @param orFilters list of filters
     * @return true if any filter matches employee
     */
    private static boolean employeeOrMatch(Employee employee, List<Filter> orFilters) {
        // OR logic within columns
        for (Filter filter : orFilters) {
            if (FilterUtil.employeeMatch(employee, filter)) {
                return true;
            }
        }
        return false;
    }
}
