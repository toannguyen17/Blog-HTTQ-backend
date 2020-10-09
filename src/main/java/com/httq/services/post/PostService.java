package com.httq.services.post;

import com.httq.model.Post;
import com.httq.services.IGeneralService;

public interface PostService extends IGeneralService<Post> {
	boolean existsBySeo(String seo);
}
