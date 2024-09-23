package employees;

import java.util.*;

public class CompanyImpl implements Company{
   private TreeMap<Long, Employee> employees = new TreeMap<>();
   private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
   private TreeMap<Float, List<Manager>> managersFactor = new TreeMap<>();

   private class CompanyImplIterator implements Iterator<Employee> {
    private Iterator<Employee> iterator = employees.values().iterator();
    private Employee prev = null;

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Employee next() {
        return prev = iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
        removeDepartment(prev);
        removeManager(prev);
    }
}

@Override
public Iterator<Employee> iterator() {
    return new CompanyImplIterator();
}

@Override
public void addEmployee(Employee empl) {
    Employee oldEmployee = employees.putIfAbsent(empl.getId(), empl);
    if (oldEmployee != null) {
        throw new IllegalStateException();
    }

    addDepartment(empl);
    addManager(empl);
}
private void addManager(Employee empl) {
    if (empl instanceof Manager manager) {
        managersFactor.computeIfAbsent(manager.getFactor(), i -> new ArrayList<>()).add(manager);
    }
}
private void addDepartment(Employee empl) {
    String department = empl.getDepartment();

    if (department != null) {
        employeesDepartment.computeIfAbsent(department, i -> new ArrayList<>()).add(empl);
    }
}

@Override
public Employee getEmployee(long id) {
    return employees.get(id);
}

@Override
public Employee removeEmployee(long id) {
    Employee removedEmpl = employees.remove(id);

    if (removedEmpl == null) {
        throw new NoSuchElementException();
    }

    removeDepartment(removedEmpl);
    removeManager(removedEmpl);
    
    return removedEmpl;
}
private void removeManager(Employee empl) {
    if (empl instanceof Manager manager) {
        Float factor = manager.getFactor();
        List<Manager> managers = managersFactor.get(factor);
        managers.remove(manager);

        if (managers.isEmpty()) {
            managersFactor.remove(factor);
        }
    }
}
private void removeDepartment(Employee empl) {
    String department = empl.getDepartment();
    if (department != null) {
        List<Employee> employees =  employeesDepartment.get(department);
        employees.remove(empl);

        if (employees.isEmpty()) {
            employeesDepartment.remove(department);
        }
    }
}

@Override
public int getDepartmentBudget(String department) {
    int sum = 0;
    List<Employee> employees = employeesDepartment.get(department);

    if (employees != null) {
        sum = employees.stream().mapToInt(i -> i.computeSalary()).sum();
    }
    return sum;
}

@Override
public String[] getDepartments() {
    return employeesDepartment.keySet().stream().sorted().toArray(String[]::new);
}

@Override
public Manager[] getManagersWithMostFactor() {
    Manager[] res = new Manager[0];
    if (!managersFactor.isEmpty()) {
        res = managersFactor.lastEntry().getValue().toArray(new Manager[0]);
    }
    return res;
}

}
