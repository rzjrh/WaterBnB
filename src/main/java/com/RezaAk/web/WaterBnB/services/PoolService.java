package com.RezaAk.web.WaterBnB.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.RezaAk.web.WaterBnB.models.Pool;
import com.RezaAk.web.WaterBnB.models.Review;
import com.RezaAk.web.WaterBnB.models.User;
import com.RezaAk.web.WaterBnB.repositories.PoolRepository;
import com.RezaAk.web.WaterBnB.repositories.ReviewRepository;
import com.RezaAk.web.WaterBnB.repositories.UserRepository;

@Service
public class PoolService {
	private PoolRepository poolRepository;
	private UserRepository userRepository;
	private ReviewRepository reviewRepository;
	
    public PoolService(PoolRepository poolRepository, UserRepository userRepository, ReviewRepository reviewRepository)     {
        this.poolRepository = poolRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }
    
    public void newPool(Pool pool, User user) {
    	pool.setHost(user);
    	this.poolRepository.save(pool);
    }
    
    public void editPool(Pool editedPool, Long id) {
    	Pool oldPool = this.poolRepository.findOne(id);
    	oldPool.setSize(editedPool.getSize());
    	oldPool.setDescription(editedPool.getDescription());
    	oldPool.setNightlyCost(editedPool.getNightlyCost());
    	this.poolRepository.save(oldPool);
    }
    
    public void addReview(Review review) {
    	 this.reviewRepository.save(review);
    }
    
    public List<Pool> getAllPools() {
    	List<Pool> pools = this.poolRepository.findAll();
    	if(pools == null) {
    		pools = new ArrayList<Pool>();
    	}
    	return pools;
    }
    
    public Pool getById(Long id) {
    	return this.poolRepository.findOne(id);
    }
    
}
