package employees;

import org.json.JSONObject;

public class WageEmployee extends Employee{
    private int wage;
    private int hours;

    public WageEmployee() {
    }

    @Override
    protected void setObject(JSONObject jsonObject) {
        super.setObject(jsonObject);
        wage = jsonObject.getInt("wage");
        hours = jsonObject.getInt("hours");
    }

    public WageEmployee(long id, int basicSalary, String department, int wage, int hours) {
        super(id, basicSalary, department);
        this.hours = hours;
        this.wage = wage;
    }

    public int getHours() {
        return hours;
    }

    public int getWage() {
        return wage;
    }

    @Override
    public int computeSalary() {
        return super.computeSalary() + wage * hours;
    }

    @Override
    protected void fillJSON(JSONObject jsonObject) {
        super.fillJSON(jsonObject);
        jsonObject.put("wage", wage);
        jsonObject.put("hours", hours);
    }
}
