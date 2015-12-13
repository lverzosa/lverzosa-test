# Filter List
## FilterUtil.employeeFilter(List<Employee> employees, List<Filter> filters)
This is the main method for the service. It reads in the list of employees and applies the filters to return
a list of employees that have been filtered by the list of filters.

### Optimizations
I frequently use HashMaps since they are typically random access (constant time) lookups especially for smaller sets
of objects (like filter operations). The tradeoff is more memory is used to cache the map instead of just streaming
the original filter list.

### Expected run time
The runtime of this service is O(n*m) where n is the number of employees and m is the number of filters. The reason
for this is for every employee potentially every filter needs to be checked.

If the EmployeeColumn is changed to be read from a database (instead of coded in), the runtime of the service could
change to O(n*m*p) where p is the number of columns in employee. However, that could be changed back to O(n*m) by
using a HashMap.

## FilterAndOrUtil.employeeFilter(List<Employee> employees, List<Filter> filters, Set<EmployeeColumn> orColumns)
This is the method for the "bonus" question. It takes in a set of columns that will be treated as or between filters.
This method works by taking the original set of filters and pulling out and dividing the or column filters into
a HashMap. The original set now just contains and filters which is processed as normal while the or set is processed
separately by column.

### Expected runtime
Preprocessing the filters into a hashmap is O(m*p) where m is the number of filters and p is the number of columns.
The reason for that is every column could potentially be in the or column and every filter needs to be checked. It's
space complexity is also O(m*p).

The running of the filtering part doesn't change once the map is created. It's runtime is O(n*m). Every employee
needs to be checked and every filter could potentially be checked.

In the end, the runtime complexity is O(m*p + n*m) or O(m(p+n)).
