package com.application.mainapp.repository;


import com.application.mainapp.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

    @Query("SELECT file FROM FileData file WHERE file.subsection.subsectionID = :subsectionID")
    Optional<FileData> findFileDataBySubsection_SubsectionID(@Param("subsectionID") Long subsectionID);
}
