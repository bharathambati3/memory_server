package com.mados.memory_server.jobs;

import com.mados.memory_server.db.entity.MemoRevisionQueue;
import com.mados.memory_server.db.entity.RevisionPatternType;
import com.mados.memory_server.db.repository.MemoRevisionQueueRepo;
import com.mados.memory_server.mediator.MemoryMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MemoryRevisionQueueJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(MemoryRevisionQueueJob.class);

    @Autowired
    MemoRevisionQueueRepo revisionQueueRepo;

    @Autowired
    MemoryMediator memoryMediator;

    @Override
    @Scheduled(fixedDelay= 10*1000)
    public void execute() {
        //get memory revisions to be updated. isRevisionDone true && curr time > nextUpdatedTime
        //for each memory set new nextUpdatedTime
        // here if its value is null then remove from queue.
        List<MemoRevisionQueue> revisions = revisionQueueRepo.getRevisionsToBeUpdated();

        for (MemoRevisionQueue revision : revisions) {
            RevisionPatternType type = revision.getMemoRecord().getType();
            LocalDateTime createdOn = revision.getMemoRecord().getCreatedOn();
            LocalDateTime currRevision = revision.getNextRevisionOn();
            LocalDateTime nextRevisionOn = memoryMediator.getNextRevisionOn(type, createdOn, currRevision);

            if (nextRevisionOn == null) {
                revisionQueueRepo.delete(revision);
            } else {
                revision.setNextRevisionOn(nextRevisionOn);
                revision.setCurrentRevisionDone(false);
                revisionQueueRepo.save(revision);
            }
        }
    }
}
