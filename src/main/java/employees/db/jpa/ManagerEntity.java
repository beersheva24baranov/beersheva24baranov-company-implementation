package employees.db.jpa;
import org.json.JSONObject;

import jakarta.persistence.*;
import employees.Employee;
import employees.Manager;
@Entity
public class ManagerEntity extends EmployeeEntity {
    private float factor;
    @Override
    protected void fromEmployeeDto(Employee empl) {
        super.fromEmployeeDto(empl);
        factor = ((Manager) empl).getFactor();
    }

    @Override
    protected void toJsonObject(JSONObject jsonObj) {
        super.toJsonObject(jsonObj);
        jsonObj.put("factor", factor);
    }
}
