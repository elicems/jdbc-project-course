import dao.DaoFactory;
import dao.SellerDAO;
import model.Seller;

public class Log {
    public static void main(String[] args){
        SellerDAO sellerDAO = DaoFactory.createSellerDao();
        System.out.println("Test n1 = sellerFindByID");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
    }
}
