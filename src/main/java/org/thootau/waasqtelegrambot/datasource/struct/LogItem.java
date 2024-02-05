package org.thootau.waasqtelegrambot.datasource.struct;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

public class LogItem {
    @Id
    public String id;

    public String logText;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private LastModifiedDate lastUpdated;

    public LogItem(String logText) {
        this.logText = logText;
    }
}
