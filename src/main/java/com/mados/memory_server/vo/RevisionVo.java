package com.mados.memory_server.vo;

import com.mados.memory_server.db.entity.RevisionPattern;

import java.time.LocalDateTime;

public class RevisionVo {

    LocalDateTime nextRevisionDate;
    RevisionPattern currentRevisionPattern;

    public RevisionVo(LocalDateTime nextRevisionDate, RevisionPattern currentRevisionPattern) {
        this.nextRevisionDate = nextRevisionDate;
        this.currentRevisionPattern = currentRevisionPattern;
    }

    public LocalDateTime getNextRevisionDate() {
        return nextRevisionDate;
    }

    public void setNextRevisionDate(LocalDateTime nextRevisionDate) {
        this.nextRevisionDate = nextRevisionDate;
    }

    public RevisionPattern getCurrentRevisionPattern() {
        return currentRevisionPattern;
    }

    public void setCurrentRevisionPattern(RevisionPattern currentRevisionPattern) {
        this.currentRevisionPattern = currentRevisionPattern;
    }
}
