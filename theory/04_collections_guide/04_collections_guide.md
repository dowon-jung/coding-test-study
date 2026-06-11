# Java 자료구조 비교 및 활용법 (Collections Guide)
> 생성일: 2026-06-11 | Claude Code theorist 에이전트로 생성

---

## 핵심 개념

자료구조 선택이 곧 풀이 속도를 결정한다. "무엇을 쓸지"보다 "언제 왜 쓰는지"를 체득하는 것이 목표다.

---

## 자료구조 한눈에 비교표

| 자료구조 | 순서 유지 | 정렬 | 중복 허용 | 핵심 접근 방식 | 탐색 | 삽입/삭제 |
|----------|----------|------|----------|----------------|------|----------|
| `ArrayList` | O (인덱스) | X (직접 정렬) | O | 인덱스 | O(n) / get: O(1) | 중간 O(n), 끝 O(1) |
| `LinkedList` | O (연결) | X | O | 순차 / 양방향 | O(n) | 앞뒤 O(1), 중간 O(n) |
| `HashSet` | X | X | X | 해시 | O(1) | O(1) |
| `TreeSet` | X | O (오름차순) | X | 레드블랙트리 | O(log n) | O(log n) |
| `HashMap` | X | X | Key: X, Value: O | 해시 | O(1) | O(1) |
| `TreeMap` | X | O (Key 오름차순) | Key: X, Value: O | 레드블랙트리 | O(log n) | O(log n) |
| `Stack` | O (LIFO) | X | O | 후입선출 | O(n) | O(1) |
| `Queue` | O (FIFO) | X | O | 선입선출 | O(n) | O(1) |
| `Deque` | O (양방향) | X | O | 양방향 큐 | O(n) | O(1) |
| `PriorityQueue` | X | O (우선순위) | O | 힙(Heap) | O(n) | O(log n) |

---

## 언제 뭘 쓸지 판단 기준

### 선택 플로우차트

```
문제를 읽는다
     |
     v
[Key-Value 매핑이 필요한가?]
  Yes --> [Key 기준 정렬이 필요한가?]
              Yes --> TreeMap
              No  --> HashMap
  No
     |
     v
[중복을 제거해야 하는가?]
  Yes --> [정렬된 상태로 유지해야 하는가?]
              Yes --> TreeSet
              No  --> HashSet
  No
     |
     v
[순서가 중요한가? (인덱스 접근)]
  Yes --> ArrayList
     |
     v
[앞/뒤 삽입·삭제가 빈번한가?]
  Yes --> [양방향이 필요한가?]
              Yes --> Deque (ArrayDeque)
              No  --> Queue (LinkedList)
     |
     v
[최솟값/최댓값 반복 추출이 필요한가?]
  Yes --> PriorityQueue
     |
     v
[괄호 검사 / 후위표기 / 되돌리기(Undo)?]
  Yes --> Stack (또는 Deque)
```

### 상황별 한 줄 판단

| 상황 | 선택 | 이유 |
|------|------|------|
| 중복 제거만 필요 | `HashSet` | O(1) 탐색·삽입 |
| 중복 제거 + 정렬 | `TreeSet` | 자동 정렬 유지 |
| 빈도 카운팅 | `HashMap<String, Integer>` | key별 값 누적 |
| 빈도 기반 정렬 | `PriorityQueue` + `HashMap` | 힙으로 상위 k개 추출 |
| BFS 탐색 | `Queue` (ArrayDeque 권장) | FIFO 구조 |
| DFS / 괄호 검사 | `Stack` 또는 `Deque` | LIFO 구조 |
| 슬라이딩 윈도우 | `Deque` | 양방향 O(1) 삽입·삭제 |
| 구간 최솟값 반복 추출 | `PriorityQueue` | 힙 자동 정렬 |
| 정렬된 범위 탐색 | `TreeMap` / `TreeSet` | `floor()`, `ceiling()` 지원 |
| 인덱스로 랜덤 접근 | `ArrayList` | O(1) get |

---

## 각 자료구조별 코테 핵심 메서드

### ArrayList
```java
List<Integer> list = new ArrayList<>();

list.add(1);               // 끝에 추가
list.add(0, 99);           // 인덱스 0에 삽입
list.get(0);               // 인덱스 접근 O(1)
list.remove(Integer.valueOf(3)); // 값으로 삭제 (인덱스 삭제와 혼동 주의)
list.size();               // 크기
list.contains(3);          // 포함 여부 O(n)
Collections.sort(list);    // 오름차순 정렬
Collections.sort(list, (a, b) -> b - a); // 내림차순
```

### HashSet
```java
Set<String> set = new HashSet<>();

set.add("apple");          // 삽입 O(1)
set.contains("apple");     // 탐색 O(1)
set.remove("apple");       // 삭제 O(1)
set.size();                // 크기
// 주의: get()은 없음! 인덱스 접근 불가
```

### TreeSet
```java
TreeSet<Integer> ts = new TreeSet<>();

ts.add(5);
ts.first();                // 최솟값
ts.last();                 // 최댓값
ts.floor(7);               // 7 이하 최댓값
ts.ceiling(3);             // 3 이상 최솟값
ts.headSet(5);             // 5 미만 원소들
ts.tailSet(5);             // 5 이상 원소들
```

### HashMap
```java
Map<String, Integer> map = new HashMap<>();

map.put("a", 1);                        // 삽입/갱신
map.get("a");                           // 조회 (없으면 null)
map.getOrDefault("b", 0);              // 없으면 기본값 반환
map.containsKey("a");                   // 키 존재 여부
map.remove("a");                        // 삭제
map.getOrDefault(key, 0) + 1;          // 빈도 카운팅 패턴

// 순회
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String k = entry.getKey();
    int v = entry.getValue();
}
```

### TreeMap
```java
TreeMap<Integer, String> tm = new TreeMap<>();

tm.put(3, "c");
tm.firstKey();             // 최소 키
tm.lastKey();              // 최대 키
tm.floorKey(5);            // 5 이하 최대 키
tm.ceilingKey(3);          // 3 이상 최소 키
tm.headMap(5);             // 키 < 5 인 부분 Map
```

### Stack
```java
Deque<Integer> stack = new ArrayDeque<>(); // Stack 클래스보다 ArrayDeque 권장

stack.push(1);             // 삽입 (top에)
stack.pop();               // 꺼내기 (top에서)
stack.peek();              // 확인만 (꺼내지 않음)
stack.isEmpty();           // 비어있는지 확인
```

### Queue
```java
Queue<Integer> queue = new LinkedList<>();
// 또는 (더 빠름)
Queue<Integer> queue = new ArrayDeque<>();

queue.offer(1);            // 삽입 (뒤에)
queue.poll();              // 꺼내기 (앞에서), 없으면 null
queue.peek();              // 앞 원소 확인만
queue.isEmpty();           // 비어있는지 확인
```

### Deque (양방향 큐)
```java
Deque<Integer> dq = new ArrayDeque<>();

dq.offerFirst(1);          // 앞에 삽입
dq.offerLast(2);           // 뒤에 삽입
dq.pollFirst();            // 앞에서 꺼내기
dq.pollLast();             // 뒤에서 꺼내기
dq.peekFirst();            // 앞 확인만
dq.peekLast();             // 뒤 확인만
```

### PriorityQueue
```java
// 최솟값 우선 (기본, Min Heap)
PriorityQueue<Integer> minPQ = new PriorityQueue<>();

// 최댓값 우선 (Max Heap)
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

// 커스텀 정렬 (2D 배열 — 첫 번째 원소 기준 오름차순)
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

minPQ.offer(3);            // 삽입 O(log n)
minPQ.poll();              // 최솟값 꺼내기 O(log n)
minPQ.peek();              // 최솟값 확인만 O(1)
minPQ.size();
```

---

## 동작 원리 요약

| 자료구조 | 내부 구현 | 특이사항 |
|----------|----------|---------|
| ArrayList | 동적 배열 | 용량 초과 시 1.5배 확장 |
| LinkedList | 이중 연결 리스트 | Queue/Deque로도 사용 가능 |
| HashSet / HashMap | 해시 테이블 | 해시 충돌 시 O(n) 가능 (실전에서는 O(1) 취급) |
| TreeSet / TreeMap | 레드블랙트리 | 항상 정렬 유지, O(log n) 보장 |
| PriorityQueue | 이진 힙 | 전체 정렬이 아닌 루트만 보장 |
| ArrayDeque | 원형 배열 | Stack·Queue 모두 대체 가능, LinkedList보다 빠름 |

---

## 자주 하는 실수 모음

### 실수 1: ArrayList 정렬 — Arrays.sort vs Collections.sort
```java
int[] arr = {3, 1, 2};
Arrays.sort(arr);               // 기본 타입 배열 (int[], long[]) 에 사용

List<Integer> list = new ArrayList<>();
Collections.sort(list);         // List 계열에 사용
// Arrays.sort(list) -> 컴파일 에러!

// 커스텀 정렬은 둘 다 람다 가능하지만 기본 타입 배열에는 불가
Integer[] boxed = {3, 1, 2};
Arrays.sort(boxed, (a, b) -> b - a); // Integer[] 는 가능
// Arrays.sort(arr, (a, b) -> b - a); // int[] 는 불가 (컴파일 에러)
```

### 실수 2: HashSet에는 get()이 없다
```java
Set<String> set = new HashSet<>();
set.add("apple");
// set.get(0); -> 컴파일 에러! 인덱스 접근 불가
// 특정 원소를 꺼내려면 iterator 또는 for-each 사용
for (String s : set) { ... }
```

### 실수 3: int[] 를 HashMap 키로 쓰면 안 된다
```java
// 잘못된 사용 — 레퍼런스 비교로 동작
Map<int[], Integer> map = new HashMap<>();
int[] key = {1, 2};
map.put(key, 1);
map.get(new int[]{1, 2}); // null 반환! (다른 객체)

// 올바른 사용 — 문자열로 변환하거나 List<Integer> 사용
Map<String, Integer> map2 = new HashMap<>();
map2.put(Arrays.toString(new int[]{1, 2}), 1); // "[1, 2]" 키로 저장

Map<List<Integer>, Integer> map3 = new HashMap<>();
map3.put(Arrays.asList(1, 2), 1); // 값 기반 equals 동작
```

### 실수 4: ArrayList.remove() — 인덱스 vs 값
```java
List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
list.remove(1);                     // 인덱스 1 삭제 → [1, 3]
list.remove(Integer.valueOf(1));    // 값 1 삭제 → [2, 3]
// int 리터럴은 인덱스로 처리되므로 값 삭제 시 반드시 Integer.valueOf() 사용
```

### 실수 5: Stack 클래스보다 ArrayDeque 사용
```java
// Stack 클래스는 Vector 상속 — 동기화 오버헤드, 느림
Stack<Integer> stack = new Stack<>(); // 코테에서도 동작하지만 느림

// ArrayDeque가 더 빠르고 권장됨
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);  // push/pop/peek 그대로 사용 가능
```

### 실수 6: PriorityQueue는 전체 정렬이 아님
```java
PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(3, 1, 2));
// pq.toString()이나 반복 출력 시 [1, 3, 2] 처럼 내부 힙 순서 노출
// 정렬된 순서로 꺼내려면 반드시 poll() 반복 사용
while (!pq.isEmpty()) {
    System.out.print(pq.poll() + " "); // 1 2 3 — 순서 보장
}
```

### 실수 7: HashMap 순회 중 수정 금지
```java
Map<String, Integer> map = new HashMap<>();
// ConcurrentModificationException 발생
for (String key : map.keySet()) {
    map.remove(key); // 순회 중 삭제 금지!
}

// 올바른 방법
map.entrySet().removeIf(e -> e.getValue() == 0);
// 또는 별도 리스트에 모아서 삭제
```

---

## 대표 코테 패턴 코드

### 패턴 1: 빈도 카운팅 + 상위 k개 추출
```java
// 문자열 배열에서 가장 많이 등장한 단어 k개 추출
public String[] topK(String[] words, int k) {
    Map<String, Integer> freq = new HashMap<>();
    for (String w : words) {
        freq.put(w, freq.getOrDefault(w, 0) + 1);
    }

    // 빈도 내림차순, 동빈도면 사전순
    PriorityQueue<String> pq = new PriorityQueue<>(
        (a, b) -> freq.get(a).equals(freq.get(b))
            ? a.compareTo(b)
            : freq.get(b) - freq.get(a)
    );
    pq.addAll(freq.keySet());

    String[] result = new String[k];
    for (int i = 0; i < k; i++) result[i] = pq.poll();
    return result;
}
```

### 패턴 2: BFS — Queue 사용
```java
public int bfs(int[][] graph, int start, int target) {
    Queue<Integer> queue = new ArrayDeque<>();
    boolean[] visited = new boolean[graph.length];

    queue.offer(start);
    visited[start] = true;
    int depth = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int cur = queue.poll();
            if (cur == target) return depth;
            for (int next : graph[cur]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        depth++;
    }
    return -1;
}
```

### 패턴 3: 중복 제거 후 정렬 결과 활용
```java
// 배열에서 중복 제거 후 k번째 작은 수
public int kthSmallest(int[] arr, int k) {
    TreeSet<Integer> ts = new TreeSet<>();
    for (int n : arr) ts.add(n);

    int idx = 0;
    for (int val : ts) {          // TreeSet은 오름차순 보장
        if (++idx == k) return val;
    }
    return -1;
}
```

### 패턴 4: 슬라이딩 윈도우 최댓값 — Deque 활용
```java
// 크기 k인 윈도우의 최댓값 배열 반환
public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> dq = new ArrayDeque<>(); // 인덱스 저장

    for (int i = 0; i < n; i++) {
        // 윈도우 벗어난 인덱스 제거
        if (!dq.isEmpty() && dq.peekFirst() < i - k + 1) dq.pollFirst();
        // 현재보다 작은 값 인덱스 제거 (최댓값 유지)
        while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) dq.pollLast();
        dq.offerLast(i);
        if (i >= k - 1) result[i - k + 1] = nums[dq.peekFirst()];
    }
    return result;
}
```

### 패턴 5: 괄호 검사 — Stack 활용
```java
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (c == ')' && top != '(') return false;
            if (c == '}' && top != '{') return false;
            if (c == ']' && top != '[') return false;
        }
    }
    return stack.isEmpty();
}
```

---

## 시간 / 공간 복잡도

| 자료구조 | 탐색 | 삽입 | 삭제 | 공간 |
|----------|------|------|------|------|
| ArrayList | O(n) / get O(1) | 끝 O(1), 중간 O(n) | O(n) | O(n) |
| LinkedList | O(n) | 앞뒤 O(1), 중간 O(n) | 앞뒤 O(1) | O(n) |
| HashSet / HashMap | O(1) 평균 | O(1) 평균 | O(1) 평균 | O(n) |
| TreeSet / TreeMap | O(log n) | O(log n) | O(log n) | O(n) |
| Stack / Queue / Deque | O(n) | O(1) | O(1) | O(n) |
| PriorityQueue | peek O(1) | O(log n) | O(log n) | O(n) |

---

## 주의사항 & 팁

- `ArrayDeque`는 Stack과 Queue를 동시에 대체할 수 있으며 `LinkedList`보다 빠르다. 코테에서는 ArrayDeque를 기본으로 쓴다.
- `HashMap`의 시간복잡도는 평균 O(1)이지만, 해시 충돌이 많으면 O(n)까지 느려질 수 있다. 코테 수준에서는 O(1)로 취급해도 무방하다.
- `PriorityQueue`의 `contains()`는 O(n)이다. 힙에서 특정 값 존재 여부를 자주 확인해야 하면 `HashSet`을 병행한다.
- `TreeSet`의 `floor()`, `ceiling()`은 코테에서 매우 유용하다. 이분탐색을 직접 구현하지 않아도 된다.
- `Collections.unmodifiableList()` 등 불변 컬렉션은 코테에서 쓸 일이 거의 없다. 무시해도 된다.
- `int[]` 배열 키는 레퍼런스 비교라 HashMap 키로 쓰면 버그 발생. 반드시 `Arrays.toString()` 또는 `List<Integer>` 변환 후 사용.
- 정렬 후 이분탐색이 필요하면 `Collections.binarySearch(list, key)` 활용 가능. 단, 정렬된 리스트에서만 유효하다.

---

## 연습 문제 (프로그래머스)

| 레벨 | 문제명 | 핵심 자료구조 | 링크 |
|------|--------|--------------|------|
| Lv1 | 폰켓몬 | HashSet (중복 제거, 종류 카운팅) | https://school.programmers.co.kr/learn/courses/30/lessons/1845 |
| Lv2 | 의상 | HashMap (카테고리별 개수 조합) | https://school.programmers.co.kr/learn/courses/30/lessons/42578 |
| Lv2 | 더 맵게 | PriorityQueue (최솟값 반복 추출) | https://school.programmers.co.kr/learn/courses/30/lessons/42626 |
| Lv2 | 기능개발 | Queue (FIFO 순서 처리) | https://school.programmers.co.kr/learn/courses/30/lessons/42586 |
| Lv2 | 주식가격 | Stack (뒤에서 비교, 단조 스택) | https://school.programmers.co.kr/learn/courses/30/lessons/42584 |
| Lv3 | 이중우선순위큐 | TreeSet (최솟값·최댓값 동시 접근) | https://school.programmers.co.kr/learn/courses/30/lessons/42628 |
