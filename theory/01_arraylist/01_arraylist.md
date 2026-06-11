# ArrayList

> 생성일: 2026-06-11 | Claude Code theorist 에이전트로 생성

---

## 핵심 개념

동적 크기 배열로, 인덱스 기반 O(1) 접근과 자동 크기 확장을 제공하는 순서 있는 컬렉션이다.

---

## 언제 쓰나?


| 상황                    | 예시 문제 유형        |
| --------------------- | --------------- |
| 크기가 미리 정해지지 않은 리스트 관리 | 조건 필터링 후 결과 수집  |
| 순서가 중요한 데이터           | 정렬, 순위 계산       |
| 인덱스로 임의 접근이 필요할 때     | 슬라이딩 윈도우, 투 포인터 |
| BFS/DFS 결과 경로 추적      | 최단 경로 저장        |
| 그래프 인접 리스트 표현         | 그래프 탐색 문제       |


---

## 동작 원리

내부적으로 배열(Object[])을 사용하며, 용량 초과 시 1.5배 크기의 새 배열로 복사한다.

```
초기 용량: 10
추가(add) → 용량 초과 → 새 배열(용량 * 1.5) 생성 → 복사 → 참조 교체
```


| 동작          | 내부 처리               | 비고                |
| ----------- | ------------------- | ----------------- |
| get(i)      | 배열 직접 접근            | O(1)              |
| add(e)      | 마지막 인덱스+1 저장        | O(1) 평균, O(n) 드물게 |
| add(i, e)   | i 이후 원소 한 칸씩 이동     | O(n)              |
| remove(i)   | i 이후 원소 한 칸씩 앞으로 이동 | O(n)              |
| contains(e) | 처음부터 선형 탐색          | O(n)              |


---

## Java 구현 템플릿

```java
import java.util.*;

// 기본 선언
List<Integer> list = new ArrayList<>();
List<String> strList = new ArrayList<>(Arrays.asList("a", "b", "c"));

// 자주 쓰는 패턴
list.add(1);                        // 끝에 추가
list.add(0, 99);                    // 인덱스에 삽입
list.remove(Integer.valueOf(1));    // 값으로 삭제 (주의: remove(1)은 인덱스 삭제)
list.remove(0);                     // 인덱스로 삭제
list.get(0);                        // 조회
list.set(0, 10);                    // 수정
list.size();                        // 크기
list.contains(5);                   // 포함 여부
list.indexOf(5);                    // 첫 번째 인덱스
Collections.sort(list);             // 오름차순 정렬
Collections.sort(list, Collections.reverseOrder()); // 내림차순

// 2D ArrayList (그래프 인접 리스트)
List<List<Integer>> graph = new ArrayList<>();
for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
graph.get(u).add(v);

// 조건 필터링 후 배열 변환
int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
Integer[] arr2 = list.toArray(new Integer[0]);
```

---

## 대표 문제 패턴

### 패턴 1: 조건 필터링 & 결과 수집

```java
// "배열에서 특정 조건을 만족하는 원소만 골라 반환"
public int[] solution(int[] arr, int divisor) {
    List<Integer> result = new ArrayList<>();
    for (int num : arr) {
        if (num % divisor == 0) result.add(num);
    }
    if (result.isEmpty()) return new int[]{-1};
    Collections.sort(result);
    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

### 패턴 2: 그래프 인접 리스트 구성

```java
// BFS/DFS를 위한 인접 리스트
public int solution(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());

    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]); // 무방향
    }

    boolean[] visited = new boolean[n + 1];
    Queue<Integer> queue = new LinkedList<>();
    queue.add(1);
    visited[1] = true;
    int count = 0;

    while (!queue.isEmpty()) {
        int cur = queue.poll();
        count++;
        for (int next : graph.get(cur)) {
            if (!visited[next]) {
                visited[next] = true;
                queue.add(next);
            }
        }
    }
    return count;
}
```

### 패턴 3: 슬라이딩 윈도우 / 투 포인터

```java
// 연속 부분 배열의 합이 target인 경우의 수
public int countSubarrays(int[] nums, int target) {
    int left = 0, sum = 0, count = 0;
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right];
        while (sum > target) sum -= nums[left++];
        if (sum == target) count++;
    }
    return count;
}
```

### 패턴 4: 커스텀 정렬

```java
// 2차원 배열 또는 객체 리스트 정렬
List<int[]> list = new ArrayList<>();
list.add(new int[]{3, 1});
list.add(new int[]{1, 5});
list.add(new int[]{2, 3});

// 첫 번째 원소 오름차순, 같으면 두 번째 원소 내림차순
list.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
```

---

## 시간 / 공간 복잡도


| 연산                    | 복잡도        | 비고           |
| --------------------- | ---------- | ------------ |
| get(index)            | O(1)       | 인덱스 직접 접근    |
| add(e) — 끝에 추가        | O(1) 평균    | 용량 확장 시 O(n) |
| add(index, e) — 중간 삽입 | O(n)       | 이후 원소 이동     |
| remove(index)         | O(n)       | 이후 원소 이동     |
| contains(e)           | O(n)       | 선형 탐색        |
| sort()                | O(n log n) | TimSort      |
| 공간                    | O(n)       | 내부 배열 크기     |


---

## 주의사항 & 팁

- `remove(int index)` vs `remove(Object o)` 혼동 주의. Integer를 값으로 삭제할 때는 `remove(Integer.valueOf(x))` 사용.
- 반복 중 삭제는 `Iterator` 또는 역방향 순회(`for i = size-1 downTo 0`)로 처리한다.
- `Arrays.asList()`는 크기 고정 리스트를 반환하므로 `add/remove` 불가. `new ArrayList<>(Arrays.asList(...))` 로 감싸야 한다.
- `List<int[]>`는 가능하지만 `List<int>`는 불가 — 기본형은 반드시 래퍼 클래스(Integer, Long 등) 사용.
- 결과를 `int[]`로 반환해야 할 때 stream 변환: `list.stream().mapToInt(Integer::intValue).toArray()`
- 그래프 문제에서 인접 리스트를 `ArrayList[]` 대신 `List<List<Integer>>` 로 쓰면 타입 안전성이 높아진다.
- 크기가 명확하면 `new ArrayList<>(capacity)`로 초기 용량을 지정해 불필요한 재할당을 줄인다.

---

## 연습 문제 (프로그래머스)


| 레벨  | 문제명            | 핵심 포인트                      | 링크                                                                                                             |
| --- | -------------- | --------------------------- | -------------------------------------------------------------------------------------------------------------- |
| Lv1 | 나누어 떨어지는 숫자 배열 | 필터링 후 정렬, 빈 결과 처리           | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=1&search=나누어+떨어지는+숫자+배열) |
| Lv1 | 두 개 뽑아서 더하기    | ArrayList에 결과 담고 중복 제거 후 정렬 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=1&search=두+개+뽑아서+더하기)    |
| Lv1 | 체육복            | 리스트로 여벌/도난 관리               | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=1&search=체육복)            |
| Lv2 | 의상             | 카테고리별 리스트 그룹핑               | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=의상)             |
| Lv2 | 숫자 블록          | 리스트 순회 + 조건 필터링             | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=숫자+블록)          |


