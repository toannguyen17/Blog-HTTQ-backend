package com.httq.services.search;

import com.httq.model.SearchResult;

import java.util.List;

public interface SearchService {
    List<SearchResult> search(String key);
}
