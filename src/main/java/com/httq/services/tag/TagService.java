package com.httq.services.tag;

import com.httq.model.Tag;
import com.httq.services.IGeneralService;
import org.springframework.stereotype.Service;

@Service
public interface TagService extends IGeneralService<Tag> {
   long countByTag(String tag);
   void deleteByTag(String tag);
}
