package employees;

import java.util.*;
import java.util.Map.Entry;
import java.lang.IllegalStateException;
import io.Persistable;
import java.io.*;

public class CompanyImpl implements Company, Persistable {
    private TreeMap<Long, Employee> employees = new TreeMap<>();
    private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
    private TreeMap<Float, List<Manager>> managers = new TreeMap<>();
    private boolean stateChanged = false;

    public class IteratorCompany implements Iterator<Employee>{
        Iterator<Employee> it = employees.values().iterator();
        Employee iteratedObj;

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Employee next() {
            iteratedObj = it.next();
            return iteratedObj;
        }

        @Override
        public void remove() {
            it.remove();
            removeEmployeeFromDepartment(iteratedObj);
            removeManagerFromManagers(iteratedObj);
            stateChanged = true;
        }
    }

    @Override
    public Iterator<Employee> iterator() {
        return new IteratorCompany();
    }

    @Override
    public void addEmployee(Employee empl) {
        Long id = empl.getId();
        if (getEmployee(id) != null) {
            throw new IllegalStateException("The employee with this id is already exist in th compamy");
        }
        employees.put(id, empl);
        addEmployeeToDepartment(empl);
        addEmployeeToManagers(empl);
        stateChanged = true;
    }

    @SuppressWarnings("unused")
    private void addEmployeeToDepartment(Employee empl) {
        employeesDepartment.computeIfAbsent(empl.getDepartment(), k -> new LinkedList<>()).add(empl);
    }

    @SuppressWarnings("unused")
    private void addEmployeeToManagers(Employee empl) {
        if (empl instanceof Manager) {
            Manager manager = (Manager) empl;
            managers.computeIfAbsent(manager.getFactor(), k -> new LinkedList<>()).add(manager);
        }
    }

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    @Override
    public Employee removeEmployee(long id) {
        Employee empl = employees.get(id);
        if (empl == null) {
            throw new NoSuchElementException("The employee with this id is not exist in the compamy");
        }
        removeEmployeeFromDepartment(empl);
        removeManagerFromManagers(empl);
        stateChanged = true;
        return employees.remove(id);
    }

    private void removeEmployeeFromDepartment(Employee empl) {
        removeFromMapsOfLists(employeesDepartment, empl, empl.getDepartment());
    }

    private void removeManagerFromManagers(Employee empl) {
        if (empl instanceof Manager) {
            Manager manager = (Manager) empl;
            removeFromMapsOfLists(managers, manager, manager.getFactor());
        }
    }

    private <K, E extends Employee> void removeFromMapsOfLists(Map<K, List<E>> map, Employee empl, K key) {
        List<E> list = map.get(key);
        list.remove(empl);
        if (list.isEmpty()) {
            map.remove(key);
        }
    }

    @Override
    public int getDepartmentBudget(String department) {
        int budget = 0;
        List<Employee> listEmployees = employeesDepartment.get(department);
        if (listEmployees != null) {
            budget = listEmployees.stream().mapToInt(e -> e.computeSalary()).sum();
        }
        return budget;
    }

    @Override
    public String[] getDepartments() {
        return employeesDepartment.keySet().stream().sorted().toArray(String[]::new);
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        Entry<Float, List<Manager>> mostFactorEntry = managers.lastEntry();
        Manager[] res = {};
        if (mostFactorEntry != null) {
            res = mostFactorEntry.getValue().stream().toArray(Manager[]::new);
        }
        return res;
    }

    @Override
    public boolean saveTofile(String fileName) {
        boolean res = false;
        if (stateChanged) {
            try (PrintWriter writer = new PrintWriter(fileName)) {
                forEach(writer::println);
                stateChanged = false;
                res = true;        
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    @Override
    public void restoreFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.lines().forEach(l -> addEmployee(Employee.getEmployeeFromJSON(l)));
        } catch (FileNotFoundException e) { 
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
