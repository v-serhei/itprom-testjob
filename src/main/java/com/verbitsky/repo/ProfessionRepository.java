package com.verbitsky.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.verbitsky.entity.ProfessionEntity;

@Repository
public interface ProfessionRepository extends CrudRepository<ProfessionEntity, Long> {
}
