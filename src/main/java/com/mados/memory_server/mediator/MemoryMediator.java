package com.mados.memory_server.mediator;

import com.mados.memory_server.constants.ErrorResultStatus;
import com.mados.memory_server.db.entity.*;
import com.mados.memory_server.db.repository.*;
import com.mados.memory_server.request.BaseResponse;
import com.mados.memory_server.request.CreateMemoVo;
import com.mados.memory_server.request.SaveRevisionVo;
import com.mados.memory_server.request.UpdateMemoVo;
import com.mados.memory_server.vo.RevisionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class MemoryMediator {

    private static final Integer DEFAULT_REVISION_TYPE_ID = 2;
    @Autowired
    MemoRecordRepo memoRecordRepo;

    @Autowired
    MemoAuditRepo memoAuditRepo;

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    MemoRevisionQueueRepo revisionQueueRepo;

    @Autowired
    RevisionPatternRepo revisionPatternRepo;

    @Autowired
    RevisionPatternTypeRepo revisionPatternTypeRepo;

    @Autowired
    MemoRevisionHistoryRepo revisionHistoryRepo;

    public BaseResponse<List<MemoRecord>> getAllNotes() {
        return new BaseResponse<>(memoRecordRepo.findAll());
    }

    public BaseResponse<MemoRecord> getMemory(Long id) {
        Optional<MemoRecord> byId = memoRecordRepo.findById(id);
        return byId.map(BaseResponse::new)
                .orElseGet(() -> new BaseResponse<>(ErrorResultStatus.INVALID_MEMO_ID, id));
    }

    @Transactional
    public BaseResponse<MemoRecord> createMemo(CreateMemoVo cmVo) {
        Optional<Topic> byId = topicRepo.findById(cmVo.getTopicId());

        if (byId.isPresent()) {

            MemoRecord memoRecord = new MemoRecord();
            memoRecord.setTopic(byId.get());
            memoRecord.setTitle(cmVo.getTitle());
            memoRecord.setContent(cmVo.getContent());
            memoRecord.setCreatedOn(LocalDateTime.now());
            if (memoRecord.getLearntOn() == null) {
                memoRecord.setLearntOn(LocalDateTime.now());
            }
            RevisionPatternType revisionType = getRevisionType(cmVo.getRevisionTypeId());
            memoRecord.setType(revisionType);
            memoRecord = memoRecordRepo.save(memoRecord);

            RevisionPattern currentRevisionPattern = getNextRevisionOn(memoRecord.getType(), null);

            if (currentRevisionPattern != null) {

                MemoRevisionQueue queue = new MemoRevisionQueue();
                queue.setMemoRecord(memoRecord);
                queue.setToBeRevisedOn(memoRecord.getCreatedOn());
                queue.setCurrentRevisionDate(memoRecord.getCreatedOn());
                queue.setNextRevisionOn(memoRecord.getCreatedOn().plusMinutes(currentRevisionPattern.getDuration()));
                queue.setCurrentRevisionPattern(currentRevisionPattern);
                revisionQueueRepo.save(queue);
            }

            MemoAudit memoAudit = new MemoAudit();
            memoAudit.setMemoRecord(memoRecord);
            memoAudit.setTitle(memoRecord.getTitle());
            memoAudit.setContent(memoRecord.getContent());
            memoAudit.setCreatedOn(LocalDateTime.now());
            memoAudit.setLearntOn(memoRecord.getLearntOn());

            memoAuditRepo.save(memoAudit);

            return new BaseResponse<>(memoRecord);
        }
        return new BaseResponse<>(ErrorResultStatus.INVALID_TOPIC_ID, cmVo.getTopicId());
    }

    private RevisionPatternType getRevisionType(Integer revisionTypeId) {
        Optional<RevisionPatternType> byId = revisionPatternTypeRepo.findById(revisionTypeId);
        if (byId.isPresent()) {
            return byId.get();
        }
        Optional<RevisionPatternType> byId1 = revisionPatternTypeRepo.findById(DEFAULT_REVISION_TYPE_ID);
        if (byId1.isPresent()) {
            return byId1.get();
        }
        System.out.println("Can't find revision patter type from given id "
                + revisionTypeId + " or from default id "+DEFAULT_REVISION_TYPE_ID);
        return null;
    }

    public RevisionPattern getNextRevisionOn(RevisionPatternType type, RevisionPattern currentRevisionPattern) {
        if (type == null) {
            return null;
        }
        List<RevisionPattern> revisionPatterns = revisionPatternRepo.findByType(type);
        if (revisionPatterns.size() == 0) {
            return null;
        }

        //revisionPatterns is ascending order of duration from query.
        if (currentRevisionPattern == null) {
            return revisionPatterns.get(0);
        }

        if (! currentRevisionPattern.getRevisionPatternType().equals(type)) {
            //auto correct. take the first revision pattern from the correct type.
            return revisionPatterns.get(0);
        }

        boolean found = false;
        for (RevisionPattern aByType : revisionPatterns) {
            if (found) {
                return aByType;
            }
            if (aByType.equals(currentRevisionPattern)) {
                found = true;
            }
        }
        return null;
    }

    public BaseResponse<MemoRecord> updateMemo(UpdateMemoVo umVo) {
        Optional<MemoRecord> byId = memoRecordRepo.findById(umVo.getMemoId());
        if (byId.isPresent()) {
            MemoRecord memoRecord = byId.get();
            if (umVo.getTitle() == null && umVo.getContent() == null) {
                return new BaseResponse<>(ErrorResultStatus.INVALID_MEMO_UPDATE_REQ);
            }

            if (umVo.getTitle() != null) {
                memoRecord.setTitle(umVo.getTitle());
            }
            if (memoRecord.getContent() != null) {
                memoRecord.setContent(umVo.getContent());
            }

            memoRecord = memoRecordRepo.save(memoRecord);
            MemoAudit memoAudit = new MemoAudit();
            memoAudit.setMemoRecord(memoRecord);
            memoAudit.setTitle(memoRecord.getTitle());
            memoAudit.setContent(memoRecord.getContent());
            memoAudit.setCreatedOn(LocalDateTime.now());
            memoAudit.setLearntOn(memoRecord.getLearntOn());

            memoAuditRepo.save(memoAudit);

            return new BaseResponse<>(memoRecord);
        }
        return new BaseResponse<>(ErrorResultStatus.INVALID_MEMO_ID, umVo.getMemoId());
    }

    public BaseResponse<List<MemoRevisionQueue>> getMemoRevisions() {
        List<MemoRevisionQueue> currentRevisableQueue = revisionQueueRepo.getCurrentRevisableQueue();
        return new BaseResponse<>(currentRevisableQueue);
    }

    public BaseResponse<MemoRevisionHistory> memoryRevised(SaveRevisionVo saveRevisionVo) {
        MemoRevisionQueue memoRevisionQueue = getRevisionQueue(saveRevisionVo.getMemoryId());
        if (memoRevisionQueue == null) {
            return new BaseResponse<>(ErrorResultStatus.NO_QUEUE_FOR_GIVEN_MEMORY_ID, saveRevisionVo.getMemoryId());
        }
        LocalDateTime nextRevisionOn = memoRevisionQueue.getNextRevisionOn();

        LocalDateTime now = LocalDateTime.now();
        //Extend revision period only when the revision is done only once in a given revision duration.
        if (nextRevisionOn == null) {
            memoRevisionQueue.setCurrentRevisionDate(now);
            memoRevisionQueue.setNextRevisionOn(now.plusMinutes(memoRevisionQueue.getCurrentRevisionPattern().getDuration()));
            revisionQueueRepo.save(memoRevisionQueue);
        }

        MemoRevisionHistory history = new MemoRevisionHistory();
        history.setToBeRevisedOn(memoRevisionQueue.getToBeRevisedOn());
        history.setRevisedOn(now);
        history.setComments(saveRevisionVo.getComments());
        history.setMemoRecord(memoRevisionQueue.getMemoRecord());
        MemoRevisionHistory revisionHistory = revisionHistoryRepo.save(history);
        return new BaseResponse<>(revisionHistory);
    }

    public MemoRevisionQueue getRevisionQueue(Long memoryId) {
        Optional<MemoRecord> byId = memoRecordRepo.findById(memoryId);
        if (byId.isPresent()) {
            MemoRevisionQueue byMemoryId = revisionQueueRepo.findByMemoryId(byId.get());
            if (byMemoryId == null) {
                return null;
            }
            return byMemoryId;
        }
        return null;
    }
}
