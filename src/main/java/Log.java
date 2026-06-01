import dao.DaoFactory;
import dao.SellerDAO;
import model.Seller;

public class Log {
    public static void main(String[] args){
        SellerDAO sellerDAO = DaoFactory.createSellerDao();
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
    }
}
