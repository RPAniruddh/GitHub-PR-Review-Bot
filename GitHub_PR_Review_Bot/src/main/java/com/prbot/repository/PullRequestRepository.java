package com.prbot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prbot.domain.PullRequest;

public interface PullRequestRepository extends JpaRepository<PullRequest, Long> {
	Optional<PullRequest> findByRepoFullNameAndPrNumber(String repoFullName, Integer prNumber);
}
