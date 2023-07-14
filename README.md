# spring-cloud-aws

#### AWS SNS-SQS 를 활용한 이벤트 기반 아키텍처 테스트


#### 사전작업
- AWS Credentials 설정
  - [DefaultCredentialsProvider](https://docs.awspring.io/spring-cloud-aws/docs/3.0.0/reference/html/index.html#defaultcredentialsprovider)
- SNS 주제 생성
- SQS 생성
  - 액세스 정책 수정 ([AWS문서](https://docs.aws.amazon.com/ko_kr/sns/latest/dg/subscribe-sqs-queue-to-sns-topic.html#SendMessageToSQS.arn))
    - 생성한 SNS ARN 허용
- SNS 구독 생성
  - 생성한 SQS 구독 연동

---

주요 관심사 또는 실시간 처리가 필요한 사항은 같은 트랜잭션 내에서 처리

비관심사의 경우, 스프링 트랜잭션 이벤트를 활용해 커밋 후 

SNS 메시지 전송

SNS -> SQS 메시지 전달

SQS 메시지 구독으로 처리 