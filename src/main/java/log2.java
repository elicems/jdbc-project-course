import dao.DaoFactory;
import dao.DepartmentDAO;
import model.Department;

import java.util.List;
import java.util.Scanner;

public class log2 {
    public static void main(String[] args){
        DepartmentDAO departmentDAO = DaoFactory.createDepartmentDao();
        Scanner t = new Scanner(System.in);

        System.out.println("Test 1 = findByID");
        Department dep1 = departmentDAO.findById(1);
        System.out.println(dep1);

        System.out.println("\nTest 2 = findAll");
        List<Department> list = departmentDAO.findAll();
        for(Department d:list){
            System.out.println(d);
        }
        System.out.println("Test 3 = insert");
        Department department = new Department(null,"Musica");
        departmentDAO.insert(department);
        System.out.println("Insert new id = " + department.getId());

        System.out.println("\nTest 4 = update");
        Department dep = departmentDAO.findById(1);
        dep.setName("D2");
        departmentDAO.update(dep);
        System.out.println("Update complete");

        System.out.println("\bTest 5 = deleteById");
        System.out.println("Enter id to delete: ");
        int id  = t.nextInt();
        departmentDAO.deleteById(id);
        System.out.println("Delete completed");
    }

}
