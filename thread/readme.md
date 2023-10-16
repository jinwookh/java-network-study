# 생성자 소비자 패턴
- 생성자 소비자 패턴의 예제다.
- 생성자는 프린트를 하고 싶을 때 큐에 프린트 요청을 보내고 큐는 이런 요청이 들어올 때마다 순서대로 이 큐에 넣어둔다.
- 소비자는 주기적으로 큐를 확인한다.
- 큐에 저장된 프린트 요청이 없다면 소비자 쓰레드는 요청이 들어올 때까지 대기하게 된다.
- JobQueue에서 synchronized block을 빼면 queue.removeFirst()를 할 때 NoSuchException이 발생한다.
- 이 예제에서는 consumer가 producer보다 먼저 시작하므로, producer가 생성을 시작할 때까지 consumer가 대기하도록 해야한다.
