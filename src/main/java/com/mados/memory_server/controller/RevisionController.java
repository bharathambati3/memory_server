package com.mados.memory_server.controller;

import com.mados.memory_server.constants.ErrorResultStatus;
import com.mados.memory_server.db.entity.RevisionPattern;
import com.mados.memory_server.db.entity.RevisionPatternType;
import com.mados.memory_server.db.repository.RevisionPatternRepo;
import com.mados.memory_server.db.repository.RevisionPatternTypeRepo;
import com.mados.memory_server.request.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RevisionController {

    @Autowired
    RevisionPatternTypeRepo revisionPatternTypeRepo;


    @Autowired
    RevisionPatternRepo revisionPatternRepo;

    @GetMapping("revision/types")
    public BaseResponse<List<RevisionPatternType>> getRevisionTypes() {
        return new BaseResponse<>(revisionPatternTypeRepo.findAll());
    }

    @GetMapping("revision/types/{id}/revisions")
    public BaseResponse<List<RevisionPattern>> getRevisionsForType(@PathVariable("id") Integer id) {
        Optional<RevisionPatternType> byId = revisionPatternTypeRepo.findById(id);
        return byId.map(revisionPatternType ->
                new BaseResponse<>(revisionPatternRepo.findByType(revisionPatternType)))
                .orElseGet(() -> new BaseResponse<>(ErrorResultStatus.INVALID_REVISION_TYPE_ID, id));
    }
}
