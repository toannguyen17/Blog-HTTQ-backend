package com.httq.services.tag;

import com.httq.model.Tag;
import com.httq.services.IGeneralService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TagService extends IGeneralService<Tag> {
   Integer countByTag(String tag);
   void deleteByTag(String tag);
   Optional<Tag> findByTag(String tag);
}
