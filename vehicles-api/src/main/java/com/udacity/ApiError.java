package com.udacity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiError {
    private final String me;
    private final List<String> er;
    ApiError(String me, List<String> er) {
        this.me = me;
        this.er = er;
    }
    public String getMessage() {
        return me;
    }

    public List<String> getErrors() {
        return er;
    }
}
