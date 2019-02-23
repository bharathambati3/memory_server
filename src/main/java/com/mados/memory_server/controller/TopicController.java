package com.mados.memory_server.controller;

import com.mados.memory_server.constants.ErrorResultStatus;
import com.mados.memory_server.db.entity.Category;
import com.mados.memory_server.db.entity.Topic;
import com.mados.memory_server.db.repository.CategoryRepo;
import com.mados.memory_server.db.repository.TopicRepo;
import com.mados.memory_server.request.BaseResponse;
import com.mados.memory_server.request.CreateTopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TopicController {

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    CategoryRepo categoryRepo;

    // Get All Notes
    @GetMapping("/topics")
    public BaseResponse<List<Topic>> getAll() {
        return new BaseResponse<>(topicRepo.findAll());
    }

    @PostMapping("/topic")
    public BaseResponse<Topic> createTopic(@Valid @RequestBody CreateTopicVo ctopic) {
        Integer categoryId = ctopic.getCategoryId();
        Optional<Category> byId = categoryRepo.findById(categoryId);
        if (byId.isPresent()) {
            Topic topic = new Topic();
            topic.setCreatedOn(LocalDateTime.now());
            topic.setLastUpdated(LocalDateTime.now());

            topic.setName(ctopic.getName());
            topic.setDescription(ctopic.getDescription());
            topic.setCategory(byId.get());
            return new BaseResponse<>(topicRepo.save(topic));
        }
        return new BaseResponse<>(ErrorResultStatus.INVALID_CATEGORY_ID, ctopic.getCategoryId());
    }

    @PutMapping("/topic")
    public BaseResponse<Topic> updateTopic(@Valid @RequestBody Topic topic) {
        Optional<Topic> byId = topicRepo.findById(topic.getId());
        if (byId.isPresent()) {
            Topic prevTopic = byId.get();
            if (topic.getName() != null) {
                prevTopic.setName(topic.getName());
            }
            if (topic.getDescription() != null) {
                prevTopic.setDescription(topic.getDescription());
            }
            prevTopic.setLastUpdated(LocalDateTime.now());
            return new BaseResponse<>(topicRepo.save(prevTopic));
        }
        return new BaseResponse<>(ErrorResultStatus.INVALID_TOPIC_ID, topic.getId());
    }
}
