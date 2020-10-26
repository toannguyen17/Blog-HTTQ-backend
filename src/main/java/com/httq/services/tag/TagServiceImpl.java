package com.httq.services.tag;

import com.httq.model.Tag;
import com.httq.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public Iterable<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public void save(Tag entity) {
        tagRepository.save(entity);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Integer countByTag(String tag) {
//        Iterable<Tag> tags = tagRepository.findAllByTagContains(tag);
        return tagRepository.countAllByTagContains(tag);
    }

    @Override
    public void deleteByTag(String tag) {
        tagRepository.deleteByTag(tag);
    }

    @Override
    public Optional<Tag> findByTag(String tag) {
        return tagRepository.findByTag(tag);
    }

    public Iterable<Tag> findAllByTagContains(String tag){
        return tagRepository.findAllByTagContains(tag);
    }
}
