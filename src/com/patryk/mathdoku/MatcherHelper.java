package com.patryk.mathdoku;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherHelper {
    public Pattern pattern;
    public Matcher matcher;

    public MatcherHelper(String regexString) {
        this.pattern = Pattern.compile(regexString);
    }

    public void initMatcher(String searchText) {
        matcher = this.pattern.matcher(searchText);
    }
}
