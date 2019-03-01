package com.mados.memory_server.db.repository;

import com.mados.memory_server.db.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepo extends JpaRepository<Memo, Long> {
}
