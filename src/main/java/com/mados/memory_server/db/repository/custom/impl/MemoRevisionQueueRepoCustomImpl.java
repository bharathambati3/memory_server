package com.mados.memory_server.db.repository.custom.impl;

import com.mados.memory_server.db.entity.MemoRecord;
import com.mados.memory_server.db.entity.MemoRevisionQueue;
import com.mados.memory_server.db.repository.custom.MemoRevisionQueueRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class MemoRevisionQueueRepoCustomImpl implements MemoRevisionQueueRepoCustom {

    @PersistenceContext
    EntityManager em;

    public List<MemoRevisionQueue> getCurrentRevisableQueue() {
        TypedQuery<MemoRevisionQueue> namedQuery =
                em.createNamedQuery("revision.active.list", MemoRevisionQueue.class);
        namedQuery.setParameter("isCurrentRevisionDone", false);
        return namedQuery.getResultList();
    }

    public MemoRevisionQueue findByMemoryId(MemoRecord memoRecord) {
        TypedQuery<MemoRevisionQueue> namedQuery =
                em.createNamedQuery("memory.queue.find.by.memory.id", MemoRevisionQueue.class);
        namedQuery.setParameter("memoRecord", memoRecord);
        try {
            return namedQuery.getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<MemoRevisionQueue> getRevisionsToBeUpdated() {
        TypedQuery<MemoRevisionQueue> namedQuery =
                em.createNamedQuery("queue.to.be.updated", MemoRevisionQueue.class);
        return namedQuery.getResultList();
    }
}
