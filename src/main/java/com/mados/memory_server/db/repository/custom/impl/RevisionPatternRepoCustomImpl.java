package com.mados.memory_server.db.repository.custom.impl;

import com.mados.memory_server.db.entity.RevisionPattern;
import com.mados.memory_server.db.entity.RevisionPatternType;
import com.mados.memory_server.db.repository.custom.RevisionPatternRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class RevisionPatternRepoCustomImpl implements RevisionPatternRepoCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<RevisionPattern> findByType(RevisionPatternType type) {
        TypedQuery<RevisionPattern> namedQuery =
                em.createNamedQuery("revision.pattern.find.by.type", RevisionPattern.class);
        namedQuery.setParameter("type", type);
        return namedQuery.getResultList();
    }
}
