package com.mados.memory_server.db.repository;

import com.mados.memory_server.db.entity.RevisionPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevisionPatternRepo extends JpaRepository<RevisionPattern, Integer> {
}
