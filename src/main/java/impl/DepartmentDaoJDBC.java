package impl;

import dao.DepartmentDAO;
import db.DB;
import db.DbExeception;
import model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDAO {
    private Connection conn;
    public DepartmentDaoJDBC(Connection conn){this.conn = conn;}
    @Override
    public void insert(Department obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into department "+
                    "(name) "+
                    "values " +
                    "(?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1,obj.getName());
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected>0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else {
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
    public void update(Department obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "update department " +
                            "set name = ? " +
                            "where id = ?"
            );
            ps.setString(1,obj.getName());
            ps.setInt(2,obj.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from department where id = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from department where id = ?");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("id"));
                dep.setName(rs.getString("name"));
                return dep;
            }
            return null;
        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from department order by name");
            rs = ps.executeQuery();
            List<Department> list = new ArrayList<>();

            while(rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("id"));
                dep.setName(rs.getString("name"));
                list.add(dep);
            }
            return list;
        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

}
