package org.example.skillboxhotels.repository;

import org.example.skillboxhotels.entity.StatRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatRecordRepository extends MongoRepository<StatRecord, String> {
}
