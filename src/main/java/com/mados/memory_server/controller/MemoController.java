package com.mados.memory_server.controller;

import com.mados.memory_server.constants.ErrorResultStatus;
import com.mados.memory_server.db.entity.Memo;
import com.mados.memory_server.db.entity.MemoAudit;
import com.mados.memory_server.db.entity.Topic;
import com.mados.memory_server.db.repository.MemoAuditRepo;
import com.mados.memory_server.db.repository.MemoRepo;
import com.mados.memory_server.db.repository.TopicRepo;
import com.mados.memory_server.request.BaseResponse;
import com.mados.memory_server.request.CreateMemoVo;
import com.mados.memory_server.request.UpdateMemoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MemoController {

    @Autowired
    MemoRepo memoRepo;

    @Autowired
    MemoAuditRepo memoAuditRepo;

    @Autowired
    TopicRepo topicRepo;

    // Get All Notes
    @GetMapping("/memories")
    public BaseResponse<List<Memo>> getAllNotes() {
        return new BaseResponse<>(memoRepo.findAll());
    }

    @PostMapping("/memory")
    public BaseResponse<Memo> createMemo(@Valid @RequestBody CreateMemoVo cmVo) {
        Optional<Topic> byId = topicRepo.findById(cmVo.getTopicId());

        if (byId.isPresent()) {

            Memo memo = new Memo();
            memo.setTopic(byId.get());
            memo.setTitle(cmVo.getTitle());
            memo.setContent(cmVo.getContent());
            memo.setCreatedOn(LocalDateTime.now());
            if (memo.getLearntOn() == null) {
                memo.setLearntOn(LocalDateTime.now());
            }
            memo = memoRepo.save(memo);
            MemoAudit memoAudit = new MemoAudit();
            memoAudit.setMemo(memo);
            memoAudit.setTitle(memo.getTitle());
            memoAudit.setContent(memo.getContent());
            memoAudit.setCreatedOn(LocalDateTime.now());
            memoAudit.setLearntOn(memo.getLearntOn());

            memoAuditRepo.save(memoAudit);


            return new BaseResponse<>(memo);
        }
        return new BaseResponse<>(ErrorResultStatus.INVALID_TOPIC_ID, cmVo.getTopicId());
    }

    @PutMapping("/memory")
    public BaseResponse<Memo> updateMemo(@Valid @RequestBody UpdateMemoVo umVo) {
        Optional<Memo> byId = memoRepo.findById(umVo.getMemoId());
        if (byId.isPresent()) {
            Memo memo = byId.get();
            if (umVo.getTitle() == null && umVo.getContent() == null) {
                return new BaseResponse<>(ErrorResultStatus.INVALID_MEMO_UPDATE_REQ);
            }

            if (umVo.getTitle() != null) {
                memo.setTitle(umVo.getTitle());
            }
            if (memo.getContent() != null) {
                memo.setContent(umVo.getContent());
            }

            memo = memoRepo.save(memo);
            MemoAudit memoAudit = new MemoAudit();
            memoAudit.setMemo(memo);
            memoAudit.setTitle(memo.getTitle());
            memoAudit.setContent(memo.getContent());
            memoAudit.setCreatedOn(LocalDateTime.now());
            memoAudit.setLearntOn(memo.getLearntOn());

            memoAuditRepo.save(memoAudit);

            return new BaseResponse<>(memo);
        }
        return new BaseResponse<>(ErrorResultStatus.INVALID_MEMO_ID, umVo.getMemoId());
    }
}
