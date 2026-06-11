# 📘 PROJECT.md — 프로젝트 전체 설명

> 이 문서는 레포 구조, 세팅 철학, 에이전트 사용법, 학습 계획을 한 곳에 정리한 문서입니다.  
> Claude Code를 어떻게 활용했는지 보여주는 포트폴리오 문서이기도 합니다.

---

## 1. 프로젝트 개요

| 항목 | 내용 |
|------|------|
| 목적 | 현대오토에버 코딩테스트 합격을 위한 2달 집중 스터디 |
| 언어 | Java (주), Python / JavaScript (자동 변환) |
| 기간 | 40 워킹데이 (약 2달) |
| 목표 | 프로그래머스 Level 2 안정화 → Level 3 도전 |
| 도구 | Claude Code 풀 활용 (CLAUDE.md / Rules / Hooks / Agents / GitHub Actions) |

---

## 2. 레포 구조

```
coding-test-study/
│
├── CLAUDE.md                  ← Claude Code 프로젝트 컨텍스트 (매 세션 자동 로드)
├── CLAUDE.local.md            ← 개인 로컬 설정 (Git 제외)
├── PROJECT.md                 ← 이 파일 (전체 설명)
├── README.md                  ← 진도 대시보드 (GitHub에서 보이는 메인)
├── PROGRESS.md                ← 40일 체크리스트
├── .gitignore
│
├── .claude/
│   ├── settings.json          ← Hooks 등록 + 허용/차단 명령어 (공식 스펙)
│   ├── settings.local.json    ← 개인 환경변수 (Git 제외)
│   ├── agents/                ← 전용 서브에이전트 5개
│   │   ├── reviewer.md        ← 풀이 리뷰
│   │   ├── theorist.md        ← 이론 md 생성
│   │   ├── tracker.md         ← 진도 관리 + 변환 트리거
│   │   ├── translator.md      ← Java → Python/JS 변환 + 차이점 설명
│   │   └── debugger.md        ← 오답 분석
│   ├── rules/                 ← 행동 규칙 (Rules)
│   │   ├── review.md          ← 리뷰 출력 형식 규칙
│   │   ├── theory.md          ← 이론 md 템플릿 규칙
│   │   └── progress.md        ← 진도 업데이트 규칙
│   └── hooks/                 ← 자동화 스크립트
│       ├── post-save.sh       ← Java 저장 시 컴파일 체크
│       └── daily-summary.sh   ← 세션 종료 시 하루 요약
│
├── .github/
│   └── workflows/
│       └── update-progress.yml ← push 시 README 진도율 자동 갱신
│
├── theory/                    ← 알고리즘 이론 정리 md (theorist 에이전트 생성)
│   ├── 01_java_basics.md
│   ├── 02_brute_force.md
│   ├── 03_sort.md
│   ├── 04_stack_queue.md
│   ├── 05_bfs_dfs.md
│   ├── 06_dp.md
│   └── 07_greedy_binary.md
│
├── problems/                  ← 풀이 코드 (Day 완료 시 3개 언어 자동 생성)
│   ├── level1/
│   │   ├── Solution.java
│   │   ├── Solution.py        ← translator 자동 생성
│   │   ├── Solution.js        ← translator 자동 생성
│   │   └── Solution_diff.md   ← 언어 비교 설명 자동 생성
│   ├── level2/
│   └── level3/
│
├── review/
│   └── mistake_log.md         ← 오답노트 (debugger 에이전트 자동 기록)
│
└── logs/                      ← 세션 로그 (날짜별)
    ├── README.md
    ├── TEMPLATE.md
    ├── questions.md            ← 의문점 누적
    └── YYYY-MM-DD.md          ← 날짜별 학습 기록
```

---

## 3. Claude Code 활용 설계

### 3-1. 세팅 계층 구조

```
CLAUDE.md          → 프로젝트 전체 컨텍스트 (항상 로드)
CLAUDE.local.md    → 개인 환경 (Git 제외, 로컬만)
settings.json      → Hooks 이벤트 등록, 명령어 허용/차단
settings.local.json → 개인 환경변수 (Git 제외)
rules/             → 특정 작업의 출력 형식·템플릿 지정
agents/            → 전문화된 서브에이전트 (자동 위임)
hooks/             → 이벤트 발생 시 자동 실행 스크립트
```

### 3-2. 서브에이전트 역할 분담

```
사용자
  │
  ├─ "리뷰해줘"          → @reviewer  (Sonnet, memory)
  ├─ "이론 정리해줘"      → @theorist  (Sonnet)
  ├─ "Day N 완료"        → @tracker   (Haiku, 빠름)
  │       └─ Java 파일 발견 시 → @translator (Sonnet)
  │                               └─ .py + .js + _diff.md 생성
  └─ "왜 틀렸어?"        → @debugger  (Sonnet, memory)
```

### 3-3. Hooks 이벤트

| 이벤트 | 트리거 | 실행 내용 |
|--------|--------|-----------|
| `PostToolUse (Write\|Edit)` | 파일 저장 시 | Java면 컴파일 체크, md면 커밋 알림 |
| `Stop` | 세션 종료 시 | 오늘 작업 파일 요약 + 커밋 메시지 제안 |

### 3-4. GitHub Actions

| 트리거 | 동작 |
|--------|------|
| `PROGRESS.md` push | 완료 Day 수 계산 → README 진도율 자동 갱신 |
| `problems/**` push | 풀이 수 계산 → README 통계 자동 갱신 |
| `theory/**` push | 이론 챕터 수 계산 → README 갱신 |

---

## 4. 학습 계획 (40 워킹데이)

### Phase 1 — Java 기초 세팅 `Day 1–5`
자료구조 문법 완전 숙지. 코테에서 쓰는 것만.

| Day | 주제 |
|-----|------|
| 1 | ArrayList / HashMap / HashSet |
| 2 | PriorityQueue / Stack / Deque |
| 3 | BufferedReader + StringTokenizer |
| 4 | 형변환 + 정렬 기초 |
| 5 | Phase 1 복습 + 기초 문제 2개 |

### Phase 2 — 기본 알고리즘 `Day 6–15`
프로그래머스 Level 1 전체 클리어 목표.

완전탐색 → 정렬 → 스택/큐 순서로. 각 유형 개념 1일 + 문제 1일.

### Phase 3 — 핵심 알고리즘 `Day 16–30`
**합/불 가르는 핵심 구간.** BFS/DFS + DP 집중.

- BFS/DFS: 최단거리·미로·네트워크 유형 반복
- DP: 1차원 → 2차원 → 배낭 순서로 확장

### Phase 4 — 추가 유형 `Day 31–36`
기초 탄탄하게 마무리. 그리디·이분탐색·투포인터.

### Phase 5 — 실전 모의고사 `Day 37–40`
시간 재고 3~5문제 세트 풀기. 감각 훈련.

### 전체 목표 수치

| 항목 | 목표 |
|------|------|
| 총 풀이 수 | ~60문제 |
| Level 1 | 15문제 |
| Level 2 | 40문제 |
| Level 3 | 5문제 |
| 이론 챕터 | 7개 |
| 하루 투자 시간 | 1~1.5시간 |

---

## 5. 일상적인 사용법

### 하루 공부 루틴

```
1. Claude Code 열기
   $ claude

2. 이론 공부 (필요할 때)
   "@theorist BFS 이론 md 만들어줘"

3. 문제 풀기
   problems/levelN/ProblemName.java 작성

4. 풀이 리뷰
   "@reviewer 이 코드 리뷰해줘" + 코드 붙여넣기

5. 틀렸으면
   "@debugger 왜 틀렸어?" + 코드 붙여넣기

6. Day 완료 처리 (진도 + 변환 한번에)
   "@tracker Day 5 완료, BFS 문제 2개 풀었어"

7. Git 커밋
   tracker가 제안한 커밋 메시지 그대로 사용
```

### 에이전트 호출 레퍼런스

| 상황 | 명령 예시 |
|------|-----------|
| 풀이 완성 후 리뷰 | `@reviewer 이 코드 리뷰해줘` |
| 이론 개념 정리 | `@theorist DP 이론 md 만들어줘` |
| Day 완료 처리 | `@tracker Day 7 완료, 정렬 2문제` |
| 오답 분석 | `@debugger 왜 시간초과 났어?` |
| 특정 파일만 변환 | `@translator problems/level2/GameMap.java 변환해줘` |
| 의문점 기록 | `logs/questions.md에 {질문} 추가해줘` |
| 세션 로그 생성 | `오늘 로그 파일 만들어줘` |

### 자주 쓰는 명령 패턴

```bash
# 진도 확인
"전체 진도 보여줘"

# 취약 유형 파악
"오답노트에서 자주 나오는 패턴 분석해줘"

# 이론 복습
"BFS 이론 파일 열어서 요약해줘"

# 모의고사 세팅
"Level 2 랜덤 문제 3개 추천해줘, 90분 세트로"
```

---

## 6. 파일별 역할 한눈에 보기

| 파일 | 누가 쓰나 | 용도 |
|------|-----------|------|
| `CLAUDE.md` | Claude Code 자동 | 프로젝트 컨텍스트 |
| `README.md` | GitHub 방문자 | 진도 대시보드 |
| `PROGRESS.md` | tracker 에이전트 | 체크리스트 |
| `theory/*.md` | theorist 에이전트 | 이론 정리 |
| `problems/**/*.java` | 내가 직접 | Java 풀이 |
| `problems/**/*.py` | translator 에이전트 | Python 자동 변환 |
| `problems/**/*.js` | translator 에이전트 | JS 자동 변환 |
| `problems/**/*_diff.md` | translator 에이전트 | 언어 비교 설명 |
| `review/mistake_log.md` | debugger 에이전트 | 오답노트 |
| `logs/YYYY-MM-DD.md` | 내가 직접 + Claude | 세션 기록 |
| `logs/questions.md` | 내가 직접 | 의문점 누적 |

---

## 7. Git 커밋 컨벤션

```
[DayN] 완료 - {주제}
[Theory] {알고리즘명} 이론 정리
[Review] {문제명} 오답노트 추가
[Refactor] {파일명} 풀이 개선
[Setup] 세팅 변경
🤖 자동 진도 업데이트 (N%)    ← GitHub Actions 자동 커밋
```

---

> 세팅 완료일: 2025-06-11  
> Claude Code 풀 활용: CLAUDE.md / Rules 3개 / Hooks 2개 / Agents 5개 / GitHub Actions
