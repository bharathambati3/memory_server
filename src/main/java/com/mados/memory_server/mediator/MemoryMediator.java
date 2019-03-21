package com.mados.memory_server.mediator;

import com.mados.memory_server.constants.ErrorResultStatus;
import com.mados.memory_server.db.entity.MemoRecord;
import com.mados.memory_server.db.entity.MemoAudit;
import com.mados.memory_server.db.entity.Topic;
import com.mados.memory_server.db.repository.MemoAuditRepo;
import com.mados.memory_server.db.repository.MemoRecordRepo;
import com.mados.memory_server.db.repository.TopicRepo;
import com.mados.memory_server.request.BaseResponse;
import com.mados.memory_server.request.CreateMemoVo;
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
}
