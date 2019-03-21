package com.mados.memory_server.db.repository;

import com.mados.memory_server.db.entity.MemoRevisionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRevisionHistoryRepo extends JpaRepository<MemoRevisionHistory, Long> {
}
