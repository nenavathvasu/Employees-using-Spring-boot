

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sathya.Model.Employee;
import com.sathya.Service.EmployeeService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // Save
    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee emp) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("info", "Saved successfully")
                .body(service.saveEmployee(emp));
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<Employee>> saveAll(@RequestBody List<Employee> emp) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("info", "Saved all Employees Successfully")
                .body(service.SaveAllEmployee(emp));
    }

    // HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Employee>> getEmployeeByIdHateoas(@PathVariable Long id) {

        Employee employee = service.getEmployeeById(id);

        EntityModel<Employee> model = EntityModel.of(employee);
        model.add(linkTo(methodOn(EmployeeController.class)
                .getEmployeeByIdHateoas(id)).withSelfRel());
        model.add(linkTo(methodOn(EmployeeController.class)
                .deleteEmployeeById(id)).withRel("delete"));
        model.add(linkTo(methodOn(EmployeeController.class)
                .updateEmployee(id, employee)).withRel("update"));
        model.add(linkTo(methodOn(EmployeeController.class)
                .getAllEmp()).withRel("getAll"));

        return ResponseEntity.ok(model);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmp() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        service.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllEmployees() {
        service.deleteAllEmployees();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        return ResponseEntity.ok(service.updateEmployee(id, employee));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> partialUpdate(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.partialUpdate(id, updates));
    }

    // Salary
    @GetMapping("/minsalary")
    public List<Employee> minSalary(@RequestParam double minsalary) {
        return service.getMinSalary(minsalary);
    }

    @GetMapping("/maxsalary")
    public List<Employee> maxSalary(@RequestParam double maxsalary) {
        return service.getMaxSalary(maxsalary);
    }

    @GetMapping("/salarybetween")
    public List<Employee> salaryBetween(
            @RequestParam double min,
            @RequestParam double max) {
        return service.getSalaryBetween(min, max);
    }

    // Email
    @GetMapping("/email")
    public Employee getByEmail(@RequestParam String email) {
        return service.getByEmail(email);
    }

    // Gender & Salary
    @GetMapping("/salary-or-gender")
    public List<Employee> salaryOrGender(
            @RequestParam double salary,
            @RequestParam String gender) {
        return service.getSalaryGreaterOrGender(salary, gender);
    }

    @GetMapping("/gender-and-salary")
    public List<Employee> genderAndSalary(
            @RequestParam String gender,
            @RequestParam double minSalary) {
        return service.getGenderAndMinSalary(gender, minSalary);
    }

    @GetMapping("/gender-or-salary-between")
    public List<Employee> genderOrSalaryBetween(
            @RequestParam String gender,
            @RequestParam double min,
            @RequestParam double max) {
        return service.getGenderOrSalaryBetween(gender, min, max);
    }

    // Sorting
    @GetMapping("/salary-asc")
    public List<Employee> salaryAsc() {
        return service.getSalaryAsc();
    }

    @GetMapping("/salary-desc")
    public List<Employee> salaryDesc() {
        return service.getSalaryDesc();
    }

    // Name
    @GetMapping("/name-ignorecase")
    public List<Employee> nameIgnoreCase(@RequestParam String name) {
        return service.getByNameIgnoreCase(name);
    }

    @GetMapping("/name-contains")
    public List<Employee> nameContains(@RequestParam String keyword) {
        return service.getByNameContaining(keyword);
    }
    @GetMapping("/filter")
    public List<Employee> filterEmployees(
            @RequestParam String department,
            @RequestParam String gender,
            @RequestParam double minSalary,
            @RequestParam double maxSalary) {

        return service.getEmployees(department, gender, minSalary, maxSalary);
    }

}
