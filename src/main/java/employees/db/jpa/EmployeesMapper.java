package employees.db.jpa;

import org.json.JSONObject;

import employees.Employee;

public class EmployeesMapper {
private static final String PACKAGE = "telran.employees.";
private static final String CLASS_NAME = "className";
private static final String ENTITY = "Entity";
private static final String PACKAGE_ENTITY = PACKAGE + "db.jpa.";


public static Employee toEmployeeDtoFromEntity(EmployeeEntity entity) {
    String entityClassName = entity.getClass().getSimpleName();
    String dtoClassName = PACKAGE + entityClassName.replaceAll("Entity", "");
    JSONObject jsonObj = new JSONObject();
    jsonObj.put(CLASS_NAME, dtoClassName);
    entity.toJsonObject(jsonObj);
    return Employee.getEmployeeFromJSON(jsonObj.toString());
}
public static EmployeeEntity toEmployeeEntityFromDto(Employee empl) {
    String dtoClassName = empl.getClass().getSimpleName();
    String entityClassName = PACKAGE_ENTITY + dtoClassName + ENTITY;
    try {
        EmployeeEntity entity = (EmployeeEntity) Class.forName(entityClassName)
                .getDeclaredConstructor()
                .newInstance();
        entity.fromEmployeeDto(empl);
        return entity;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}
