package edu.iu.habahram.ducksservice.repository;

import edu.iu.habahram.ducksservice.model.DuckData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DucksDBRepository
        extends CrudRepository<DuckData, Integer> {
    List<DuckData> findByTypeIgnoreCase(String type);
}