package com.application.mainapp.repository;


import com.application.mainapp.model.Subsection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsectionRepository extends JpaRepository<Subsection, Long> {
}
