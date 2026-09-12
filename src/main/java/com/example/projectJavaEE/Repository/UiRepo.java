package com.example.projectJavaEE.Repository;

import com.example.projectJavaEE.Entites.UI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UiRepo extends JpaRepository<UI, Integer> {
    public UI getByLogin_IdAccount(int id);
}
