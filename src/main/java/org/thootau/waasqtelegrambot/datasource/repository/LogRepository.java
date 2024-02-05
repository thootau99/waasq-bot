package org.thootau.waasqtelegrambot.datasource.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.thootau.waasqtelegrambot.datasource.struct.LogItem;


@Repository
public interface LogRepository extends MongoRepository<LogItem, String> { }
