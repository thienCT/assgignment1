package com.example.t2303e.dao;

import com.example.t2303e.entity.Student;
import com.example.t2303e.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements DaoInterface<Student> {
    @Override
    public List<Student> all() {
        String sql = "SELECT * FROM students";
        ArrayList<Student> list = new ArrayList<>();
        try {
            Database db = Database.createInstance();
            ResultSet rs = db.getStatement().executeQuery(sql);
            while (rs.next()){
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("telephone"),
                        rs.getInt("class_id")
                ));
            }
        }catch (Exception e){

        }
        return list;
    }

    @Override
    public boolean create(Student student) {
        String sql = "INSERT INTO students(name,email,address,telephone,class_id) VALUES(?,?,?,?,?)";
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);
            prst.setString(1,student.getName());
            prst.setString(2,student.getEmail());
            prst.setString(3,student.getAddress());
            prst.setString(4,student.getTelephone());
            prst.setInt(5,student.getClass_id());
            prst.execute();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Student student) {
        String sql = "UPDATE student set name=?, email=?, address=?, telephone=? WHERE id=?";
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);
            prst.setString(1,student.getName());
            prst.setString(2,student.getEmail());
            prst.setString(3,student.getAddress());
            prst.setString(4,student.getTelephone());
            prst.setInt(5,student.getId());
            prst.execute();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Student student) {
        String sql = "DELETE FROM student WHERE id=?";
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);
            prst.setInt(1, student.getId());
            prst.execute();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public <K> Student find(K id) {
        String sql = "SELECT * FROM student WHERE id=?";
        Student student = null;
        try {
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql){
                if (id instanceof Integer) {
                    prst.setInt(1, (Integer) id);
                } else {
                    throw new IllegalArgumentException("id must be an integer");
                }
                ResultSet rs = prst.executeQuery();
                if (rs.next()) {
                    student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setAddress(rs.getString("address"));
                    student.setName(rs.getString("name"));
                    student.getEmail(rs.getString("email"));
                    prst.execute(sql);
                }
            }
            return student;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
