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
@RequestMapping("api/v1/tags")
public class TagServiceApi {
    @Autowired
    private TagService tagService;

    @GetMapping("all")
    public ResponseEntity<BaseResponse<Iterable<Tag>>> getAllTags() {
        BaseResponse<Iterable<Tag>> baseResponse = new BaseResponse<>();
        Iterable<Tag>               tags         = tagService.findAll();
        if (!tags.iterator().hasNext()) {
            baseResponse.setStatus(51);
            baseResponse.setMsg("FOUND NO TAG");
        } else {
            baseResponse.setStatus(20);
            baseResponse.setMsg("TAGS WERE FOUND");
        }
        baseResponse.setData(tags);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse<Tag>> deleteTagById(@PathVariable("id") Long id) {
        BaseResponse<Tag> baseResponse = new BaseResponse<>();
        Optional<Tag>     tag          = tagService.findById(id);
        if (!tag.isPresent()) {
            baseResponse.setStatus(52);
            baseResponse.setMsg("TAG DOES NOT EXIST");
            baseResponse.setData(null);
        } else {
            tagService.deleteById(id);
            baseResponse.setStatus(20);
            baseResponse.setMsg("DELETE TAG");
            baseResponse.setData(tag.get());
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Tag>> addTag(@RequestParam("tag") String tag) {
        BaseResponse<Tag> baseResponse = new BaseResponse<>();
        if (tagService.findByTag(tag).isPresent()){
            baseResponse.setStatus(32);
            baseResponse.setMsg("TAG EXISTED");
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        if (tag.replace(" ", "").length() > 0) {
            Tag t = new Tag();
            t.setTag(tag);
            tagService.save(t);
            baseResponse.setStatus(20);
            baseResponse.setMsg("TAG SAVED");
            baseResponse.setData(t);
        } else {
            baseResponse.setStatus(31);
            baseResponse.setMsg("TAG CANNOT BE BLANK");
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
