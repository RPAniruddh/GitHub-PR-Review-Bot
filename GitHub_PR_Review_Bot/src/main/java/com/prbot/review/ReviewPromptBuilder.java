package com.prbot.review;

import org.springframework.stereotype.Component;

@Component
public class ReviewPromptBuilder {
    public String buildPrompt(String parsedDiff){
        return String.format("""
                You are a senior software engineer reviewing a pull request.
                Analyze the following diff and identify issues.
                
                For each issue return a JSON array with objects containing:
                - severity: one of CRITICAL, WARNING, INFO
                - category: one of SECURITY, PERFORMANCE, READABILITY
                - filePath: the file path from the diff
                - lineNumber: the line number of the issue
                - suggestion: a clear explanation of the issue and how to fix it
                
                Respond with ONLY a JSON array. No explanation, no markdown, no code blocks.
                example :
                [
                  {
                    "severity": "CRITICAL",
                    "category": "SECURITY",
                    "filePath": "src/main/java/App.java",
                    "lineNumber": 42,
                    "suggestion": "This method exposes a SQL injection vulnerability"
                  }
                ]
                
                Diff:
                %s
                """,parsedDiff);
    }
}
