package com.prbot.dto;

import com.prbot.domain.ReviewCategory;
import com.prbot.domain.ReviewSeverity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFeedback {
    private ReviewSeverity severity;
    private ReviewCategory category;
    private String filePath;
    private Integer lineNumber;
    private String suggestion;
}
