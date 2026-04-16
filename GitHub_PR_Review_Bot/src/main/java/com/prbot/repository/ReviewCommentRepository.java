package com.prbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prbot.domain.Review;
import com.prbot.domain.ReviewComment;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
	List<ReviewComment> findByReview(Review review);
}
