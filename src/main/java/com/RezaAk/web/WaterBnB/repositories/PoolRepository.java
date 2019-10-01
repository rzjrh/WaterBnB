package com.RezaAk.web.WaterBnB.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.RezaAk.web.WaterBnB.models.Pool;

public interface PoolRepository extends CrudRepository<Pool, Long>{
	List<Pool> findAll();
}
