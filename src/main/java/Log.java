import dao.DaoFactory;
import dao.SellerDAO;
import model.Department;
import model.Seller;

import java.util.List;

public class Log {
    public static void main(String[] args){
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
    }
}
