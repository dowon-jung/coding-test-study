# HashSet
> 생성일: 2026-06-11 | Claude Code theorist 에이전트로 생성

---

## 핵심 개념

중복 없는 원소 집합을 해시 기반으로 관리하여 O(1) 삽입/삭제/존재 여부 확인을 제공하는 컬렉션이다.

---

## 언제 쓰나?

| 상황 | 예시 문제 유형 |
|------|--------------|
| 중복 제거 | 배열에서 유일한 원소만 추출 |
| 방문 여부 추적 | BFS/DFS visited 처리 |
| 두 집합의 교집합 / 합집합 / 차집합 | 뉴스 클러스터링, 집합 연산 |
| O(1) 포함 여부 확인 | 특정 값이 목록에 있는지 빠른 판단 |
| 중복 없는 경우의 수 카운팅 | 폰켓몬, 유일 문자 수 계산 |

---

## 동작 원리

내부적으로 `HashMap<E, Object>`를 사용하며, 키만 활용하고 값에는 더미 객체(PRESENT)를 저장한다.

```
add(e) 호출
  → e.hashCode() 로 버킷 결정
  → 버킷 내 e.equals() 로 중복 확인
  → 중복 없으면 저장, 중복이면 무시
```

| HashMap과의 관계 | 설명 |
|-----------------|------|
| 내부 구조 | HashMap<E, Object> |
| 키 | 저장하는 원소 |
| 값 | static final Object PRESENT (더미) |
| 중복 판단 기준 | hashCode() + equals() 동시 충족 |

```
HashSet 삽입 흐름:
add("apple")
  → "apple".hashCode() = 93029210
  → 버킷 [93029210 % 16 = 10] 탐색
  → 동일 hashCode + equals인 원소 없음
  → 저장 완료

add("apple") 재시도
  → 동일 버킷 탐색
  → equals("apple") = true → 이미 존재 → 무시
```

---

## Java 구현 템플릿

```java
import java.util.*;

// 기본 선언
Set<Integer> set = new HashSet<>();
Set<String> strSet = new HashSet<>(Arrays.asList("a", "b", "c"));

// 자주 쓰는 패턴
set.add(1);                         // 삽입 (중복이면 false 반환)
set.remove(1);                      // 삭제
set.contains(1);                    // 포함 여부 O(1)
set.size();                         // 크기
set.isEmpty();                      // 비어있는지

// 배열 → Set (중복 제거)
int[] arr = {1, 2, 2, 3, 3, 3};
Set<Integer> unique = new HashSet<>();
for (int x : arr) unique.add(x);

// Set → List (정렬 필요 시)
List<Integer> sortedList = new ArrayList<>(set);
Collections.sort(sortedList);

// 집합 연산
Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
Set<Integer> b = new HashSet<>(Arrays.asList(3, 4, 5, 6));

Set<Integer> union = new HashSet<>(a);
union.addAll(b);                    // 합집합 {1,2,3,4,5,6}

Set<Integer> intersection = new HashSet<>(a);
intersection.retainAll(b);          // 교집합 {3,4}

Set<Integer> difference = new HashSet<>(a);
difference.removeAll(b);            // 차집합 {1,2}

// 삽입 순서 유지가 필요하면 LinkedHashSet
Set<String> ordered = new LinkedHashSet<>();

// 정렬 순서 유지가 필요하면 TreeSet
Set<Integer> sorted = new TreeSet<>();     // 오름차순
Set<Integer> desc = new TreeSet<>(Collections.reverseOrder()); // 내림차순
```

---

## 대표 문제 패턴

### 패턴 1: 중복 제거 & 유일 원소 카운팅

```java
// "폰켓몬" 유형 — 중복 없는 종류 수 vs 선택 가능 개수
public int solution(int[] nums) {
    Set<Integer> kinds = new HashSet<>();
    for (int n : nums) kinds.add(n);

    int pick = nums.length / 2;
    return Math.min(kinds.size(), pick); // 최대한 다양하게 선택
}
```

### 패턴 2: BFS/DFS visited 처리

```java
// 방문 처리에 Set을 사용하는 패턴 (노드 번호가 비연속적이거나 문자열일 때 유용)
public int solution(String[] words, String begin, String target) {
    Queue<String> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    queue.add(begin);
    visited.add(begin);
    int step = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String cur = queue.poll();
            if (cur.equals(target)) return step;
            for (String word : words) {
                if (!visited.contains(word) && canTransform(cur, word)) {
                    visited.add(word);
                    queue.add(word);
                }
            }
        }
        step++;
    }
    return 0;
}

private boolean canTransform(String a, String b) {
    int diff = 0;
    for (int i = 0; i < a.length(); i++) {
        if (a.charAt(i) != b.charAt(i)) diff++;
    }
    return diff == 1;
}
```

### 패턴 3: 집합 연산 (교집합 / 합집합)

```java
// "뉴스 클러스터링" 유형 — 자카드 유사도 계산
public int solution(String str1, String str2) {
    List<String> a = makeMultiSet(str1);
    List<String> b = makeMultiSet(str2);

    if (a.isEmpty() && b.isEmpty()) return 65536;

    // 교집합: 각 원소에 대해 min(count in a, count in b)
    Map<String, Integer> freqA = new HashMap<>(), freqB = new HashMap<>();
    for (String s : a) freqA.merge(s, 1, Integer::sum);
    for (String s : b) freqB.merge(s, 1, Integer::sum);

    int intersection = 0, union = 0;
    Set<String> allKeys = new HashSet<>();
    allKeys.addAll(freqA.keySet());
    allKeys.addAll(freqB.keySet());

    for (String key : allKeys) {
        int ca = freqA.getOrDefault(key, 0);
        int cb = freqB.getOrDefault(key, 0);
        intersection += Math.min(ca, cb);
        union += Math.max(ca, cb);
    }
    return (int)(65536 * ((double) intersection / union));
}

private List<String> makeMultiSet(String str) {
    List<String> list = new ArrayList<>();
    str = str.toLowerCase();
    for (int i = 0; i < str.length() - 1; i++) {
        String pair = str.substring(i, i + 2);
        if (pair.chars().allMatch(Character::isLetter)) list.add(pair);
    }
    return list;
}
```

### 패턴 4: O(1) 포함 여부로 배열 탐색 최적화

```java
// 배열 lookup을 O(n)에서 O(1)로 줄이기
// 나쁜 예: Arrays.asList(arr).contains(x) — O(n)
// 좋은 예: Set으로 변환 후 contains()

public boolean hasDuplicateWithinK(int[] nums, int k) {
    Set<Integer> window = new HashSet<>();
    for (int i = 0; i < nums.length; i++) {
        if (window.contains(nums[i])) return true;
        window.add(nums[i]);
        if (window.size() > k) window.remove(nums[i - k]);
    }
    return false;
}
```

---

## 시간 / 공간 복잡도

| 연산 | 평균 복잡도 | 최악 복잡도 | 비고 |
|------|------------|------------|------|
| add(e) | O(1) | O(n) | 해시 충돌 최악 시 |
| remove(e) | O(1) | O(n) | |
| contains(e) | O(1) | O(n) | |
| size() | O(1) | O(1) | |
| 전체 순회 | O(n) | O(n) | |
| addAll() (합집합) | O(m) | O(m*n) | m = 추가할 컬렉션 크기 |
| retainAll() (교집합) | O(n) | O(n*m) | n = 현재 set 크기 |
| 공간 | O(n) | O(n) | |

---

## 주의사항 & 팁

- **순서 보장 없음.** 순서가 필요하면 `LinkedHashSet`(삽입 순서), `TreeSet`(정렬 순서) 사용.
- 커스텀 객체를 HashSet에 넣으려면 `hashCode()`와 `equals()` 반드시 재정의해야 중복이 제대로 걸러진다.
- `int[]` 배열은 내용 기반 hashCode를 지원하지 않으므로 `Set<int[]>`는 중복 제거가 안 됨. `String`으로 변환하거나 `List<Integer>` 사용.
- `retainAll()`, `removeAll()`은 인자로 받은 컬렉션이 `ArrayList`이면 O(n*m)이 된다. 인자를 먼저 `HashSet`으로 변환하면 O(n)으로 최적화 가능.
- BFS visited를 `boolean[]` 대신 `Set`으로 쓰면 노드 번호가 문자열이거나 좌표 쌍일 때 편리하다. 단, `boolean[]`이 가능한 상황이면 `boolean[]`이 더 빠름.
- `TreeSet`의 `first()`, `last()`, `floor()`, `ceiling()`, `headSet()`, `tailSet()` 메서드는 범위 탐색 문제에서 매우 유용하다.
- 중복 허용 집합(multiset) 이 필요하면 `Map<E, Integer>` 로 빈도 관리한다.

---

## 연습 문제 (프로그래머스)

| 레벨 | 문제명 | 핵심 포인트 | 링크 |
|------|--------|-------------|------|
| Lv1 | 폰켓몬 | 중복 제거 후 종류 수 카운팅 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=1&search=폰켓몬) |
| Lv2 | 뉴스 클러스터링 | 합집합/교집합 크기 계산 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=뉴스+클러스터링) |
| Lv2 | 단어 변환 | BFS + Set으로 방문 처리 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=단어+변환) |
| Lv2 | 메뉴 리뉴얼 | 조합 생성 후 Set으로 중복 관리 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=메뉴+리뉴얼) |
| Lv1 | 중복된 숫자 개수 | contains() O(1) 활용 기초 | [풀러가기](https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=1&search=중복된+숫자+개수) |
