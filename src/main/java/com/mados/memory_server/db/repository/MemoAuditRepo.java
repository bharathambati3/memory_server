package com.mados.memory_server.db.repository;

import com.mados.memory_server.db.entity.MemoAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoAuditRepo extends JpaRepository<MemoAudit, Long> {
}
