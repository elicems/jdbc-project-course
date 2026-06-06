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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    "select seller.*, department.name as DepName " +
                            "from seller " +
                            "inner join department " +
                            "on seller.department_id = department.id " +
                            "where seller.id = ?"
            );
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs,dep);
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

    private Seller instantiateSeller(ResultSet rs, Department dep)throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setBaseSalary(rs.getDouble("base_salary"));
        obj.setBirthDate(rs.getDate("birth_date").toLocalDate());
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs)throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("department_id"));
        dep.setName(rs.getString("depName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "select seller.*, department.name as DepName " +
                            "from seller " +
                            "inner join department " +
                            "on seller.department_id = department.id " +
                            "order by name"
            );
            rs = ps.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();

            while(rs.next()){
                Department dep = map.get(rs.getInt("department_id"));

                if(dep==null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("department_id"),dep);
                }
                Seller obj = instantiateSeller(rs,dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "select seller.*, department.name as DepName " +
                            "from seller " +
                            "inner join department " +
                            "on seller.department_id = department.id " +
                            "where department.id = ? " +
                            "order by name"
            );
            ps.setInt(1,department.getId());
            rs = ps.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();

            while(rs.next()){
                Department dep = map.get(rs.getInt("department_id"));

                if(dep==null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("department_id"),dep);
                }
                Seller obj = instantiateSeller(rs,dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }
}
