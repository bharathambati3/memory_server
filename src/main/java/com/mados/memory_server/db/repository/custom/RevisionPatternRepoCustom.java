package com.mados.memory_server.db.repository.custom;

import com.mados.memory_server.db.entity.RevisionPattern;
import com.mados.memory_server.db.entity.RevisionPatternType;

import java.util.List;

public interface RevisionPatternRepoCustom {
    List<RevisionPattern> findByType(RevisionPatternType type);
}
