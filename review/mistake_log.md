# 📝 오답노트 — mistake_log.md

> 틀린 문제, 막힌 문제 기록  
> Claude Code로 추가: "오답노트에 {문제명} 추가해줘"

---

<!-- 형식:
## {문제명} — Level{N}
- **날짜**: YYYY-MM-DD
- **유형**: BFS/DFS/DP/완탐/정렬/...
- **틀린 이유**: 
- **핵심 배운 점**: 
- **다시 풀기**: [ ]
-->

## 나누어 떨어지는 숫자 배열 — Level1
- **날짜**: 2026-06-11
- **유형**: 정렬 / 구현
- **틀린 이유**:
  1. `import java.util.ArrayList`, `import java.util.Collections` 누락 — `ArrayList`/`Collections` 심볼 인식 불가
  2. 변수명 불일치: `ArrayList`를 `res`로 선언하고 `Collections.sort(list)`, `list.size()`에서 존재하지 않는 `list`를 참조
  3. 반환값 오류: 반환 타입이 `int[]`인데 `return res` (ArrayList) 를 반환
  4. 엣지케이스 누락: 나누어 떨어지는 수가 없을 때 `{-1}` 반환 조건 미처리
- **핵심 배운 점**: `java.util.*`에서 사용하는 클래스는 개별 import 필수이며, 변수명은 선언과 사용처를 일관되게 맞춰야 한다
- **다시 풀기**: [ ]

---
