package com.mados.memory_server.db.repository.custom;

import com.mados.memory_server.db.entity.MemoRecord;
import com.mados.memory_server.db.entity.MemoRevisionQueue;

import java.util.List;

public interface MemoRevisionQueueRepoCustom {

    List<MemoRevisionQueue> getCurrentRevisableQueue();

    MemoRevisionQueue findByMemoryId(MemoRecord memoRecord);

    List<MemoRevisionQueue> getRevisionsToBeUpdated();
}
