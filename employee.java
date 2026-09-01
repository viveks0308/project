import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void displayEmployee() {
        System.out.println("ID         : " + id);
        System.out.println("Name       : " + name);
        System.out.println("Department : " + department);
        System.out.println("Salary     : " + salary);
        System.out.println("----------------------------");
    }
}

public class Main {

    static ArrayList<Employee> employees = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    viewEmployees();
                    break;

                case 3:
                    searchEmployee();
                    break;

                case 4:
                    updateEmployee();
                    break;

                case 5:
                    deleteEmployee();
                    break;

                case 6:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        scanner.close();
    }

    // Add Employee
    static void addEmployee() {

        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        // Check duplicate ID
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                System.out.println("Employee ID already exists!");
                return;
            }
        }

        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        Employee employee =
                new Employee(id, name, department, salary);

        employees.add(employee);

        System.out.println("Employee added successfully!");
    }

    // View Employees
    static void viewEmployees() {

        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }

        System.out.println("\n===== EMPLOYEE LIST =====");

        for (Employee employee : employees) {
            employee.displayEmployee();
        }
    }

    // Search Employee
    static void searchEmployee() {

        System.out.print("Enter Employee ID to search: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Employee employee : employees) {

            if (employee.getId() == id) {
                System.out.println("\nEmployee Found:");
                employee.displayEmployee();
                return;
            }
        }

        System.out.println("Employee not found!");
    }

    // Update Employee
    static void updateEmployee() {

        System.out.print("Enter Employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Employee employee : employees) {

            if (employee.getId() == id) {

                System.out.print("Enter New Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter New Department: ");
                String department = scanner.nextLine();

                System.out.print("Enter New Salary: ");
                double salary = scanner.nextDouble();
                scanner.nextLine();

                employee.setName(name);
                employee.setDepartment(department);
                employee.setSalary(salary);

                System.out.println("Employee updated successfully!");
                return;
            }
        }

        System.out.println("Employee not found!");
    }

    // Delete Employee
    static void deleteEmployee() {

        System.out.print("Enter Employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employeeToDelete = null;

        for (Employee employee : employees) {

            if (employee.getId() == id) {
                employeeToDelete = employee;
                break;
            }
        }

        if (employeeToDelete != null) {
            employees.remove(employeeToDelete);
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("Employee not found!");
        }
    }
}
