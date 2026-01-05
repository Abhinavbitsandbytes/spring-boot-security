package com.example.productioReady.productioReady.services;

import com.example.productioReady.productioReady.dto.PostDTO;
import com.example.productioReady.productioReady.entities.PostEntity;
import com.example.productioReady.productioReady.exceptions.ResourceNotFoundExceptions;
import com.example.productioReady.productioReady.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // this will create constructor and do DI
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream()
                .map(entity -> modelMapper.map(entity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        // Step 1: Convert DTO → Entity
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);

        // Step 2: Persist entity
        PostEntity savedPost = postRepository.save(postEntity);

        // Step 3: Convert Entity → DTO
        PostDTO responsePost = modelMapper.map(savedPost, PostDTO.class);

        return responsePost;
    }

    @Override
    public PostDTO getPostById(Long postId) {
//        PostEntity postEntity = postRepository.findById(postId); // it will return a optional here

        PostEntity postEntity = postRepository.
                findById(postId).
                orElseThrow(() -> new ResourceNotFoundExceptions("post Not found with id" + postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }

}
