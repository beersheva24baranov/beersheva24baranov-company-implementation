package employees;

import org.json.JSONObject;

public class Manager extends Employee {
    private float factor;

    public Manager() {
    }

    @Override
    protected void setObject(JSONObject jsonObject) {
        super.setObject(jsonObject);
        factor = jsonObject.getFloat("factor");
    }
    
    public Manager(long id, int basicSalary, String department, float factor) {
        super(id, basicSalary, department);
        this.factor = factor;
    }

    public float getFactor() {
        return factor;
    }

    @Override
    public int computeSalary() {
        return (int) (super.computeSalary() * factor);
    }

    @Override
    protected void fillJSON(JSONObject jsonObject) {
        super.fillJSON(jsonObject);
        jsonObject.put("factor", factor);
    }

}
