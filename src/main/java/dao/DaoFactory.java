package dao;

import impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDAO createSellerDao(){
        return new SellerDaoJDBC();
    }
}
