package com.mados.memory_server.controller;

import com.mados.memory_server.db.entity.MemoRecord;
import com.mados.memory_server.db.entity.MemoRevisionHistory;
import com.mados.memory_server.db.entity.MemoRevisionQueue;
import com.mados.memory_server.mediator.MemoryMediator;
import com.mados.memory_server.request.BaseResponse;
import com.mados.memory_server.request.CreateMemoVo;
import com.mados.memory_server.request.SaveRevisionVo;
import com.mados.memory_server.request.UpdateMemoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    @Autowired
    MemoryMediator memoryMediator;

    // Get All Notes
    @GetMapping("/memories")
    public BaseResponse<List<MemoRecord>> getAllNotes() {
        return memoryMediator.getAllNotes();
    }

    @GetMapping("/memory/{id}")
    public BaseResponse<MemoRecord> getMemory(@PathVariable("id") Long id) {
        return memoryMediator.getMemory(id);
    }

    @PostMapping("/memory")
    public BaseResponse<MemoRecord> createMemo(@Valid @RequestBody CreateMemoVo cmVo) {
        return memoryMediator.createMemo(cmVo);
    }

    @PutMapping("/memory")
    public BaseResponse<MemoRecord> updateMemo(@Valid @RequestBody UpdateMemoVo umVo) {
        return memoryMediator.updateMemo(umVo);
    }

    @GetMapping("memory/revisions")
    public BaseResponse<List<MemoRevisionQueue>> getMemoRevisions() {
        return memoryMediator.getMemoRevisions();
    }

    @PostMapping("memory/revised")
    public BaseResponse<MemoRevisionHistory> memoryRevised(@Valid @RequestBody SaveRevisionVo saveRevisionVo) {
        return memoryMediator.memoryRevised(saveRevisionVo);
    }
}
