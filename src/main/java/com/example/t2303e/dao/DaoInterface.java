package com.example.t2303e.dao;

import java.util.List;

public interface DaoInterface<S> {
    List<S> all();
    boolean create(S s);
    boolean update(S s);
    boolean delete(S s);
    <K> S find(K id);
}
