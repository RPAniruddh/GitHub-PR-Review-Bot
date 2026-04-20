package com.prbot.review;

import com.prbot.dto.ReviewFeedback;
import com.prbot.github.DiffParser;
import com.prbot.github.GitHubClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewService {

    private final GitHubClient gitHubClient;
    private final DiffParser diffParser;
    private final ReviewPromptBuilder reviewPromptBuilder;
    private  final OpenAiClient openAiClient;

    public List<ReviewFeedback> reviewPullRequest(String repoFullName, Integer prNumber){
        String pullRequest = gitHubClient.fetchPullRequestDiff(repoFullName, prNumber);
        if(null == pullRequest){
            log.warn("The pull request has no changes in it");
            return List.of();
        }

        String codeChanges = diffParser.parse(pullRequest);
        String prompt = reviewPromptBuilder.buildPrompt(codeChanges);
        return openAiClient.getReview(prompt);
    }
}
