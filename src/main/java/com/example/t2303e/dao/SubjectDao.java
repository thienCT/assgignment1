package com.example.t2303e.dao;

import com.example.t2303e.entity.Subject;
import com.example.t2303e.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao implements DaoInterface<Subject> {
    @Override
    public List<Subject> all() {
        List<Subject> list = new ArrayList<Subject>();
        String sql = "select * from subject";
        try {
            Database db = Database.createInstance();
            ResultSet rs = db.getStatement().executeQuery(sql);
            while (rs.next()) {
                list.add(new Subject(
                        rs.getInt("id"),
                        rs.getString("Name"),
                        rs.getString("hours"),
                        ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Subject subject) {
        String sql = "insert into subject(id, name , hours) values(?,?,?,?)";
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);
            prst.setString(1, subject.getName());
            prst.setInt(2,subject.getId());
            prst.setString(3,subject.getHours());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean update(Subject subject) {
        String sql = "update subject set name=?,hours=? where id=?";
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);
            prst.setInt(1,subject.getId());
            prst.setString(2, subject.getName());
            prst.setString(3, subject.getName());
            prst.execute();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(Subject subject) {
        String sql = "delete from subject where id=?";
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);
            prst.setInt(1,subject.getId());
            prst.execute();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public <K> Subject find(K id) {
        String sql = "select * from subject where id=?";
        Subject subject = null;
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);
            if (id instanceof Integer) {
                prst.setInt(1,(Integer) id);
            }else {
                throw new IllegalArgumentException("Id must be  integer");
            }
            ResultSet rs = prst.executeQuery();
            if (rs.next()){
                subject = new Subject();
                subject.setName(rs.getString("name"));
                subject.setHours(rs.getString("hour"));
                subject.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return subject;
    }
}
