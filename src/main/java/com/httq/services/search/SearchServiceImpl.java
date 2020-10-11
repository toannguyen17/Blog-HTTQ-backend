package com.httq.services.search;

import com.httq.model.*;
import com.httq.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{
    @Autowired
    PostSearchRepository postSearchRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserSearchRepository userSearchRepository;

    @Autowired
    UserInfoSearchRepository userInfoSearchRepository;

    public List<SearchResult> search(String key) {
        List<SearchResult> results = new ArrayList<>();
        Iterable<Tag>      tags    = tagRepository.findAllByTagContains(key);
        if (tags.iterator().hasNext()) {
            for (Tag t : tags) {
                Iterable<Post> posts = postSearchRepository.findAllByTitleContainsOrSubTitleContainsOrContentPlainTextContainsOrTagsContains(key, key, key, t);
                addPostResult(results, posts);
            }
        } else {
            Iterable<Post> posts = postSearchRepository.findAllByTitleContainsOrSubTitleContainsOrContentPlainTextContains(key, key, key);
            addPostResult(results, posts);
        }

        Iterable<User> users = userSearchRepository.findAllByEmailContains(key);
        for (User u: users){
            SearchResult sr = new SearchResult();
            sr.setType("USER");
            sr.setTitle(u.getEmail());
            sr.setDescription(u.getEmail());
            sr.setReferenceId(u.getId().toString());
            results.add(sr);
        }

        Iterable<UserInfo> userInfos = userInfoSearchRepository.findAllByFirstNameContainsOrLastNameContains(key,key);
        for (UserInfo u : userInfos){
            SearchResult sr = new SearchResult();
            sr.setType("USER");
            sr.setTitle(u.getFirstName() + " " + u.getLastName());
            sr.setDescription(u.getGender() + " | " + u.getAddress());
            sr.setReferenceId(u.getUser().getId().toString());
            results.add(sr);
        }
        return results;
    }

    private void addPostResult(List<SearchResult> results, Iterable<Post> posts) {
        for (Post p : posts) {
            SearchResult sr = new SearchResult();
            sr.setType("POST");
            sr.setTitle(p.getTitle());
            sr.setDescription(p.getSubTitle());
            sr.setReferenceId(p.getSeo());
            results.add(sr);
        }
    }
}
