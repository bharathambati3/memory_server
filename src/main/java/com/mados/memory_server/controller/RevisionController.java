package com.mados.memory_server.controller;

import com.mados.memory_server.db.entity.RevisionPatternType;
import com.mados.memory_server.db.repository.RevisionPatternTypeRepo;
import com.mados.memory_server.request.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RevisionController {

    @Autowired
    RevisionPatternTypeRepo revisionPatternTypeRepo;

    @GetMapping("revision/types")
    public BaseResponse<List<RevisionPatternType>> getRevisionTypes() {
        return new BaseResponse<>(revisionPatternTypeRepo.findAll());
    }
}
