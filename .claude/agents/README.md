# 🤖 서브에이전트 목록

> Claude Code에서 `@에이전트명` 으로 직접 호출하거나, 자연어로 요청하면 자동 위임

---

| 에이전트 | 호출 방법 | 역할 | 모델 |
|---------|-----------|------|------|
| `@reviewer` | "리뷰해줘", 코드 붙여넣기 | 풀이 코드 리뷰 + 시간복잡도 + 추천 문제 | Sonnet |
| `@theorist` | "BFS 이론 정리해줘" | 이론 md 생성 → theory/ 저장 | Sonnet |
| `@tracker` | "Day 3 완료", "진도 확인" | PROGRESS + README 갱신 + **Python/JS 자동 변환 트리거** | Haiku |
| `@translator` | tracker가 자동 호출 | Java → Python + JS 변환, 같은 폴더에 저장 | Sonnet |
| `@debugger` | "왜 틀렸어?", 오류 코드 | 오답 분석 + mistake_log.md 자동 기록 | Sonnet |

---

## 에이전트 체인 구조

```
@tracker "Day 5 완료"
    │
    ├─ PROGRESS.md 체크
    ├─ README.md 갱신
    └─ @translator 위임 ──→ Solution.java 옆에
                              Solution.py 생성
                              Solution.js 생성
```

---

## 사용 예시

```
# Day 완료 (진도 + 자동 변환 한번에)
@tracker Day 5 완료, BFS 문제 2개 풀었어

# 풀이 리뷰
@reviewer 이 코드 리뷰해줘
[코드 붙여넣기]

# 이론 정리
@theorist BFS 이론 md 만들어줘

# 오답 분석
@debugger 이 코드 왜 시간초과 났어?
[코드 붙여넣기]

# 직접 변환 (특정 파일만)
@translator problems/level2/GameMap.java 변환해줘
```

---

## 파일 구조 예시 (Day 완료 후)

```
problems/level2/
├── GameMap.java     ← 원본
├── GameMap.py       ← translator 자동 생성
└── GameMap.js       ← translator 자동 생성
```
