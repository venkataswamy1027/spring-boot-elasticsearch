package com.restapi.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.restapi.model.DocumentEntity;

public interface DocumentRepository extends ElasticsearchRepository<DocumentEntity, String> {

}
