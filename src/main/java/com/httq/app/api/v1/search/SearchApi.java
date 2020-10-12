package com.httq.app.api.v1.search;

import com.httq.dto.BaseResponse;
import com.httq.dto.search.SearchKey;
import com.httq.model.SearchResult;
import com.httq.services.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/search")
public class SearchApi {
    @Autowired
    private SearchService searchService;

    @PostMapping
    public ResponseEntity<BaseResponse<List<SearchResult>>> search(@RequestBody SearchKey key) {
        BaseResponse<List<SearchResult>> baseResponse = new BaseResponse<>();
        List<SearchResult>               results      = searchService.search(key.getKey());
        if (results.size() > 0) {
            baseResponse.setStatus(20);
            baseResponse.setMsg("FOUND SOMETHINGS");
        } else {
            baseResponse.setStatus(51);
            baseResponse.setMsg("FOUND NOTHING MATCH");
        }
        baseResponse.setData(results);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
