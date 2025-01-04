package employees.db;

import java.util.List;

import employees.Employee;

public interface CompanyRepository {
    List<Employee> getEmployees();
}
