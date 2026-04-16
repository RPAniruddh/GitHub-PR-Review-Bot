package com.prbot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prbot.domain.PullRequest;
import com.prbot.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Optional<Review> findByPullRequest(PullRequest pullRequest);
}
