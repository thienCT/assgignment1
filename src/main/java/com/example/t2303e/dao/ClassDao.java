package com.example.t2303e.dao;
import com.example.t2303e.database.Database;
import com.example.t2303e.entity.Classroom;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClassDao implements DaoInterface<Classroom> {
    @Override
    public List<Classroom> all() {
        String sql = "SELECT * FROM classes";
        ArrayList<Classroom> list = new ArrayList<>();
        try {
            Database db = Database.createInstance();
            ResultSet rs = db.getStatement().executeQuery(sql);
            while (rs.next()){
                list.add(new Classroom(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        }catch (Exception e){

        }
        return list;
    }

    @Override
    public boolean create(Classroom classroom) {
        String sql = "INSERT INTO classes VALUES(?,?)";
        boolean rowInserted = false;
        try {
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);
            prst.setString(1, classroom.getName());
            prst.setInt(2,classroom.getId());

            rowInserted = prst.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowInserted;
    }

    @Override
    public boolean update(Classroom classroom) {
        String sql = "UPDATE classes SET name = ? WHERE id = ?";
        boolean rowUpdated = false;
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql);

            prst.setInt(1,classroom.getId());
            prst.setString(2,classroom.getName());

            rowUpdated = prst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowUpdated;

    }

    @Override
    public boolean delete(Classroom classroom) {
        String sql = "DELETE FROM classes WHERE id = ?";
        boolean rowDeleted = false;
        try{
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql){

                prst.setInt(1,classroom.getId());
                rowDeleted = prst.executeUpdate() > 0;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    @Override
    public <K> Classroom find(K id) {
        String sql = "SELECT * FROM classes WHERE id = ?";
        Classroom classroom = null;

        try {
            Database db = Database.createInstance();
            PreparedStatement prst = db.getPrepareStatement(sql){
                if (id instanceof Integer)
                {
                    prst.setInt(1,(Integer)id);
                }else {
                    throw new IllegalArgumentException("id must be an integer");
                }
                ResultSet rs = prst.executeQuery(sql);
                if (rs.next()){
                    classroom = new Classroom();
                    classroom.setId(rs.getInt("id"));
                    classroom.setName(rs.getString("name"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classroom;
    }
}
