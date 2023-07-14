package com.example.springcloudaws.domain.user;

import com.example.springcloudaws.domain.post.Post;
import com.example.springcloudaws.domain.post.PostRepository;
import com.example.springcloudaws.domain.post.PostStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.listener.MessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventHandler {

    private final PostRepository postRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void changePostStatus(User.UserLeaveEvent event) {
        log.debug("change post status from user leave event. {}", event);
        // 주요 관심사 또는 실시간 처리 필요한 부분은 같은 트랜잭션 내부에서 처리
        postRepository.updateStatusBy(PostStatus.DELETED, event.userId());
    }

    private final SnsTemplate snsTemplate;

    @Async(value = "AsyncTaskExecutor")
    @TransactionalEventListener
    public void sendToSns(User.UserLeaveEvent event) throws JsonProcessingException {
        log.debug("send to SNS from user leave event. {}", event);
        // 그 외 비관심사는 메시지(이벤트) 발행 (SNS)
//        Message<User.UserLeaveEvent> message = MessageBuilder.withPayload(event).build();
//        snsTemplate.send("topic-arn", message);
        // TODO HTTP 통신 + ThreadPool 문제 처리 방안 검토
        String json = mapper.writeValueAsString(event);
        snsTemplate.sendNotification("topic-arn", json, "subject");
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public void onMessage(Message<Object> message) {
        // 비관심사 구독, 후처리
        log.debug("receive from topic-arn SQS event : {}", message);
        User.UserLeaveEvent event;
        try {
            event = mapper.readValue((String) message.getPayload(), User.UserLeaveEvent.class); // type
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("error occurred while parsing payload.", e);
        }
        Long userId = event.userId();
        List<Post> posts = postRepository.findAllByUserId(userId);
        /**
         * ...
         *
         */
        postRepository.deleteAllInBatch(posts);
    }
}
