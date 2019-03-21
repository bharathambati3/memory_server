package com.mados.memory_server.mediator;

import com.mados.memory_server.constants.ErrorResultStatus;
import com.mados.memory_server.db.entity.*;
import com.mados.memory_server.db.repository.*;
import com.mados.memory_server.request.BaseResponse;
import com.mados.memory_server.request.CreateMemoVo;
import com.mados.memory_server.request.SaveRevisionVo;
import com.mados.memory_server.request.UpdateMemoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class MemoryMediator {

    @Autowired
    MemoRecordRepo memoRecordRepo;

    @Autowired
    MemoAuditRepo memoAuditRepo;

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    MemoRevisionQueueRepo revisionQueueRepo;

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
        return new BaseResponse<>(ErrorResultStatus.INVALID_TOPIC_ID, cmVo.getTopicId());
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
        Optional<MemoRevisionQueue> byId = revisionQueueRepo.findById(saveRevisionVo.getId());
        if (! byId.isPresent()) {
            return new BaseResponse<>(ErrorResultStatus.NO_QUEUE_FOR_GIVEN_ID, saveRevisionVo.getId());
        }
        MemoRevisionQueue memoRevisionQueue = byId.get();
        memoRevisionQueue.setCurrentRevisionDone(true);
        revisionQueueRepo.save(memoRevisionQueue);
        MemoRevisionHistory history = new MemoRevisionHistory();
        history.setRevisedOn(LocalDateTime.now());
        history.setComments(saveRevisionVo.getComments());
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
