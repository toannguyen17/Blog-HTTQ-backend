package com.httq.app.api.v1.tag;

import com.httq.dto.BaseResponse;
import com.httq.model.Tag;
import com.httq.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//Status:
//51 - No entity found
//4 - Access denied
//3 -
//20 - OK
//1 -
@RestController
@RequestMapping(value = "/api/v1/tag/")
public class TagServiceApi {
    @Autowired
    private TagService tagService;

    @GetMapping(value = "/all-tags")
    public ResponseEntity<BaseResponse<Iterable<Tag>>> getAllTags() {
        BaseResponse<Iterable<Tag>> baseResponse = new BaseResponse<>();
        Iterable<Tag>               tags         = tagService.findAll();
        if (tags.iterator().hasNext()) {
            baseResponse.setStatus(51);
            baseResponse.setMsg("Found no tag.");
        } else {
            baseResponse.setStatus(20);
            baseResponse.setMsg("Tags were loaded.");
        }
        baseResponse.setData(tags);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/tags/{id}")
    public ResponseEntity<BaseResponse<Tag>> deleteTagById(@PathVariable("id") Long id) {
        BaseResponse<Tag> baseResponse = new BaseResponse<>();
        Optional<Tag>     tag          = tagService.findById(id);
        if (!tag.isPresent()) {
            baseResponse.setStatus(52);
            baseResponse.setMsg("Tag does not exist.");
            baseResponse.setData(null);
        } else {
            tagService.deleteById(id);
            baseResponse.setStatus(20);
            baseResponse.setMsg("Deleted tag.");
            baseResponse.setData(tag.get());
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }



}
