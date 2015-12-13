package com.lverzosa.filtering;

import com.google.common.collect.Lists;
import com.lverzosa.filtering.data.Employee;
import com.lverzosa.filtering.data.EmployeeColumn;
import com.lverzosa.filtering.data.Filter;
import com.lverzosa.filtering.filterlogic.FilterOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lorenz on 12/13/15.
 */
public final class FilterUtil {

    private FilterUtil() {
    }

    /**
     * Main method that takes in a list of employees and returns a list of the employees filtered by the set of filters.
     * This uses an AND operation between filters.
     *
     * @param employees list of employees
     * @param filters   list of filters
     * @return filtered employees by filters
     */
    public static List<Employee> filterEmployees(List<Employee> employees, List<Filter> filters) {
        LinkedList<Employee> filteredList = Lists.newLinkedList();
        for (Employee employee : employees) {
            if (employeeMatch(employee, filters)) {
                filteredList.add(employee);
            }
        }
        return filteredList;
    }

    /**
     * Method that returns true of employee matches filters.
     *
     * @param employee employee to match
     * @param filters  filters to match against employee
     * @return true if filters pass employee
     */
    public static boolean employeeMatch(Employee employee, List<Filter> filters) {
        for (Filter filter : filters) {
            if (!employeeMatch(employee, filter)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method that returns true if filter matches employee.
     *
     * @param employee employee to match
     * @param filter   filter to match
     * @return true if filter pases employee
     */
    public static boolean employeeMatch(Employee employee, Filter filter) {
        EmployeeColumn employeeColumn = EmployeeColumn.valueOf(filter.getFieldId());

        return FilterOperation.findOperator(filter)
                .match(employeeColumn.getFilterValue(filter),
                        employee.getValue(employeeColumn));

    }

}
