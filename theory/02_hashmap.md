# HashMap
> 생성일: 2026-06-11 | Claude Code theorist 에이전트로 생성

---

## 핵심 개념

Key-Value 쌍을 해시 기반으로 저장하여 평균 O(1) 조회/삽입/삭제를 제공하는 비순서 맵이다.

---

## 언제 쓰나?

| 상황 | 예시 문제 유형 |
|------|--------------|
| 빈도 수 카운팅 | 전화번호 목록, 의상, 베스트앨범 |
| 값 → 인덱스 역매핑 | Two Sum, 완주하지 못한 선수 |
| 그룹핑 / 분류 | 같은 키를 가진 항목 묶기 |
| 중복 체크 (값과 함께 저장) | 폰켓몬, 중복 제거 + 정보 보존 |
| DP 메모이제이션 | 키가 복잡한 상태를 문자열로 인코딩 |
| 문자열 패턴 매칭 | 아나그램 판별 |

---

## 동작 원리

```
Key → hashCode() → 버킷 인덱스 결정 → 해당 버킷에 Entry(K, V) 저장
충돌 시: 같은 버킷에 LinkedList(또는 Java8+ 에서 TreeNode) 로 연결
```

| 단계 | 설명 |
|------|------|
| put(k, v) | k.hashCode() 로 버킷 결정 → equals()로 중복 확인 → 저장/갱신 |
| get(k) | k.hashCode() 로 버킷 이동 → equals() 로 키 일치 확인 → 값 반환 |
| 부하율 초과(0.75) | 버킷 수를 2배로 늘리고 전체 재해싱(rehashing) |

- 기본 버킷 수: 16
- 부하율(load factor): 0.75 (버킷 75% 이상 사용 시 확장)
- Java 8+: 한 버킷에 8개 이상 충돌 시 LinkedList → Red-Black Tree 전환 (최악 O(log n))

---

## Java 구현 템플릿

```java
import java.util.*;

// 기본 선언
Map<String, Integer> map = new HashMap<>();

// 자주 쓰는 패턴
map.put("a", 1);                            // 삽입/갱신
map.get("a");                               // 조회 (없으면 null)
map.getOrDefault("b", 0);                  // 없으면 기본값 반환
map.containsKey("a");                       // 키 존재 여부
map.containsValue(1);                       // 값 존재 여부
map.remove("a");                            // 삭제
map.size();                                 // 크기

// 빈도 카운팅 핵심 패턴
map.put(key, map.getOrDefault(key, 0) + 1);
// 또는 Java 8+
map.merge(key, 1, Integer::sum);

// 순회
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String k = entry.getKey();
    int v = entry.getValue();
}
for (String key : map.keySet()) { }
for (int value : map.values()) { }

// putIfAbsent: 키가 없을 때만 삽입
map.putIfAbsent("c", 0);

// computeIfAbsent: 키가 없으면 함수 실행 후 삽입 (그룹핑에 유용)
Map<String, List<String>> group = new HashMap<>();
group.computeIfAbsent("key", k -> new ArrayList<>()).add("value");

// 정렬된 맵이 필요할 때
Map<String, Integer> sorted = new TreeMap<>(map);                         // 키 오름차순
Map<String, Integer> reverseSorted = new TreeMap<>(Collections.reverseOrder()); // 키 내림차순
```

---

## 대표 문제 패턴

### 패턴 1: 빈도 카운팅

```java
// "완주하지 못한 선수" 유형
public String solution(String[] participant, String[] completion) {
    Map<String, Integer> map = new HashMap<>();
    for (String p : participant) {
        map.put(p, map.getOrDefault(p, 0) + 1);
    }
    for (String c : completion) {
        map.put(c, map.get(c) - 1);
        if (map.get(c) == 0) map.remove(c);
    }
    return map.keySet().iterator().next();
}
```

### 패턴 2: 그룹핑 (값으로 분류)

```java
// "베스트앨범" 유형 — 장르별 그룹핑
public int[] solution(String[] genres, int[] plays) {
    Map<String, Integer> totalPlay = new HashMap<>();
    Map<String, List<int[]>> songMap = new HashMap<>();

    for (int i = 0; i < genres.length; i++) {
        totalPlay.merge(genres[i], plays[i], Integer::sum);
        songMap.computeIfAbsent(genres[i], k -> new ArrayList<>())
               .add(new int[]{plays[i], i});
    }

    // 장르 총 재생 수 내림차순 정렬
    List<String> sortedGenres = new ArrayList<>(totalPlay.keySet());
    sortedGenres.sort((a, b) -> totalPlay.get(b) - totalPlay.get(a));

    List<Integer> result = new ArrayList<>();
    for (String genre : sortedGenres) {
        List<int[]> songs = songMap.get(genre);
        songs.sort((a, b) -> b[0] != a[0] ? b[0] - a[0] : a[1] - b[1]);
        for (int i = 0; i < Math.min(2, songs.size()); i++) {
            result.add(songs.get(i)[1]);
        }
    }
    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

### 패턴 3: 역방향 인덱스 매핑

```java
// 배열에서 두 수의 합이 target인 인덱스 찾기
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>(); // 값 → 인덱스
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        map.put(nums[i], i);
    }
    return new int[]{};
}
```

### 패턴 4: 값 기준 정렬 (Map 항목 정렬)

```java
// 빈도 높은 순으로 정렬
Map<String, Integer> freq = new HashMap<>();
// ... freq 구성 ...

List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
entries.sort((a, b) -> b.getValue() - a.getValue()); // 빈도 내림차순

for (Map.Entry<String, Integer> e : entries) {
    System.out.println(e.getKey() + ": " + e.getValue());
}
```

---

## 시간 / 공간 복잡도

| 연산 | 평균 복잡도 | 최악 복잡도 | 비고 |
|------|------------|------------|------|
| put(k, v) | O(1) | O(n) | 해시 충돌 최악 시 |
| get(k) | O(1) | O(n) | 해시 충돌 최악 시 |
| remove(k) | O(1) | O(n) | |
| containsKey(k) | O(1) | O(n) | |
| 전체 순회 | O(n + 버킷수) | O(n + 버킷수) | entrySet() 순회 |
| 공간 | O(n) | O(n) | n = 저장된 Entry 수 |

---

## 주의사항 & 팁

- `get()`은 키가 없으면 `null`을 반환한다. NPE 방지를 위해 `getOrDefault()` 습관화.
- HashMap은 순서 보장 없음. 삽입 순서 유지가 필요하면 `LinkedHashMap`, 키 정렬이 필요하면 `TreeMap`.
- `int` 배열이나 `int[]`는 `hashCode()`가 내용 기반이 아니므로 키로 쓰면 안 됨. 문자열로 변환해서 사용.
- 동시성이 필요한 환경에서는 `ConcurrentHashMap` 사용 (코딩테스트에서는 거의 불필요).
- `Map.Entry` 정렬 시 `comparingByValue()` 활용 가능: `entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()))`.
- 키가 없는데 `map.get(key) - 1` 연산하면 NPE 발생. 반드시 존재 여부 확인 후 연산.
- `merge(key, 1, Integer::sum)` 은 카운팅의 가장 깔끔한 표현이다.

---

## 연습 문제 (프로그래머스)

| 레벨 | 문제명 | 핵심 포인트 | 링크 |
|------|--------|-------------|------|
| Lv1 | 완주하지 못한 선수 | 빈도 카운팅, getOrDefault 패턴 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=1&search=완주하지+못한+선수) |
| Lv2 | 전화번호 목록 | 접두사 검색, containsKey 활용 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=전화번호+목록) |
| Lv2 | 위장 | 그룹별 카운팅 + 경우의 수 계산 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=위장) |
| Lv3 | 베스트앨범 | 다중 기준 정렬 + computeIfAbsent 그룹핑 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=3&search=베스트앨범) |
| Lv2 | 오픈채팅방 | 변경 이력 추적, 최종 상태 매핑 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=오픈채팅방) |
