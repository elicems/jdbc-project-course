package impl;

import dao.SellerDAO;
import db.DB;
import db.DbExeception;
import model.Department;
import model.Seller;

import java.sql.*;
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
    public void insert(Seller obj){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "insert into seller " +
                            "(name,email,birth_date,base_salary,department_id) " +
                            "values " +
                            "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1,obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3,new java.sql.Date(obj.getBirthDate().getYear()));
            ps.setDouble(4,obj.getBaseSalary());
            ps.setInt(5,obj.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected>0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbExeception("Unexpected error! No rows affected");
            }
        }
        catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Seller obj) {

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
