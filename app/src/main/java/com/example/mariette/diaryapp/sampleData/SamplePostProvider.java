package com.example.mariette.diaryapp.sampleData;

import com.example.mariette.diaryapp.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplePostProvider {

    public static List<Post> postList;
    public static Map<String, Post> postMap;

    static{//static initializer...
        postList = new ArrayList<>();
        postMap = new HashMap<>();

        addPost(new Post(null, null, "First Post", "Words."));
        addPost(new Post(null, null, "Second post with a slightly longer title right here look at it lol period period period", "Words."));
    }

    public static void addPost(Post post) {
        postList.add(post);
        postMap.put(post.getPostId(), post);
    }
}
