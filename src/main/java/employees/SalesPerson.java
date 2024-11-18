package employees;

import org.json.JSONObject;

public class SalesPerson extends WageEmployee {
    private float percent;
    private long sales;

    public SalesPerson() {
    }

    @Override
    protected void setObject(JSONObject jsonObject) {
        super.setObject(jsonObject);
        percent = jsonObject.getFloat("percent");
        sales = jsonObject.getLong("sales");
    }

    public SalesPerson(long id, int basicSalary, String department, int wage, int hours, float percent, long sales) {
        super(id, basicSalary, department, wage, hours);
        this.percent = percent;
        this.sales = sales;
    }

    public float getPercent() {
        return percent;
    }

    public long getSales() {
        return sales;
    }

    @Override
    public int computeSalary() {
        return super.computeSalary() + (int) (sales * percent / 100);
    }

    @Override
    protected void fillJSON(JSONObject jsonObject) {
        super.fillJSON(jsonObject);
        jsonObject.put("percent", percent);
        jsonObject.put("sales", sales);
    }

}
