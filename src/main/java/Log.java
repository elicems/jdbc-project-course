import dao.DaoFactory;
import dao.SellerDAO;
import model.Department;
import model.Seller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Log {
    public static void main(String[] args){
        Scanner t = new Scanner(System.in);
        SellerDAO sellerDAO = DaoFactory.createSellerDao();
        System.out.println("Test n1 = sellerFindByID");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);

        System.out.println("\nTest n2 = findByDepartment");
        Department department = new Department(2,null);
        List<Seller> list = sellerDAO.findByDepartment(department);
        for(Seller obj:list){
            System.out.println(obj) ;
        }
        System.out.println("\nTest n3 = findAll");
        list = sellerDAO.findAll();
        for(Seller obj:list){
            System.out.println(obj);
        }
        System.out.println("\nTest n4 = insert");
        Seller seller1 = new Seller(null,"greg","greg@gmail.com", LocalDate.now(),4000.0,department);
        sellerDAO.insert(seller1);
        System.out.println("Inserted new id = " + seller1.getId());

        System.out.println("\nTest n5 = update");
        seller = sellerDAO.findById(1);
        seller.setName("Martha Waine");
        sellerDAO.update(seller);
        System.out.println("Update completed");

        System.out.println("\nTest n6 = delete");
        System.out.println("Enter id for delete test: ");
        int id = t.nextInt();
        sellerDAO.deleteById(id);
        System.out.println("Delete completed!");
        t.close();

    }
}
