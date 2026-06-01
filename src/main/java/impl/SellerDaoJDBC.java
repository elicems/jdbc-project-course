package impl;

import dao.SellerDAO;
import db.DB;
import db.DbExeception;
import model.Department;
import model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SellerDaoJDBC implements SellerDAO {
    private Connection conn;
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "select seller.*,department.name as DepName " +
                            "from seller inner join department" +
                            "on seller.departmentId = department.id" +
                            "where seller.id = ?"
            );
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("departmentId"));
                dep.setName(rs.getString("depName"));
                Seller obj = new Seller();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setEmail(rs.getString("email"));
                obj.setBaseSalary(rs.getDouble("baseSalary"));
                obj.setBirthDate((LocalDate) rs.getObject("birthDate"));
                obj.setDepartment(dep);
                return obj;
            }
            return null;
        } catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }
}
