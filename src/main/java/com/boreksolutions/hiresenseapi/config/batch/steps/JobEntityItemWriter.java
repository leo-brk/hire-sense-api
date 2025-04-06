package com.boreksolutions.hiresenseapi.config.batch.steps;

import com.boreksolutions.hiresenseapi.core.job.JobEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class JobEntityItemWriter implements ItemWriter<JobEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends JobEntity> items) throws Exception {
        for (JobEntity item : items) {
            entityManager.persist(item);  // Persist each entity individually
        }
        entityManager.flush();  // Ensure all entities are flushed to the database
        entityManager.clear();  // Clear the persistence context to free memory
    }
}

