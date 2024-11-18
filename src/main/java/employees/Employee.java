package employees;

import org.json.JSONObject;

public class Employee {
    private long id;
    private int basicSalary;
    private String department;

    @SuppressWarnings("unchecked")
    static public Employee getEmployeeFromJSON(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String className = jsonObject.getString("className");
        try {
            Class<Employee> clazz = (Class<Employee>) Class.forName(className);
            Employee empl = clazz.getConstructor().newInstance();
            empl.setObject(jsonObject);
            return empl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void setObject(JSONObject jsonObject) {
        id = jsonObject.getLong("id");
        basicSalary = jsonObject.getInt("basicSalary");
        department = jsonObject.getString("department");
    }

    public Employee() {
    }

    public Employee(long id, int basicSalary, String department) {
        this.id = id;
        this.basicSalary = basicSalary;
        this.department = department;
    }

    public int computeSalary() {
        return basicSalary;
    }

    public long getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((Employee) obj).id;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("className", getClass().getName());
        fillJSON(jsonObject);
        return jsonObject.toString();
    }

    protected void fillJSON(JSONObject jsonObject) {
        jsonObject.put("id", id);
        jsonObject.put("basicSalary", basicSalary);
        jsonObject.put("department", department);
    }
}
