package com.prbot.github;

import org.springframework.stereotype.Component;

@Component
public class DiffParser {
    public String parse(String rawDiff){

        String[] lines = rawDiff.split("\n");
        StringBuilder stringBuilder = new StringBuilder();

        for(String line : lines){
            if(line.startsWith("@@") || (line.startsWith("+") && !line.startsWith("+++")) ||
                    (line.startsWith("-") && !line.startsWith("---"))){
                stringBuilder.append(line).append("\n");
            }
        }

        return stringBuilder.toString();
    }
}
