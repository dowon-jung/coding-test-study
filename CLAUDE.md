# CLAUDE.md — 코딩테스트 스터디 프로젝트

> Claude Code가 이 프로젝트를 열 때 자동으로 읽는 컨텍스트 파일

---

## 프로젝트 개요

- **목적**: 현대오토에버 코딩테스트 합격을 위한 2달 집중 스터디
- **언어**: Java (모든 풀이는 Java로 작성)
- **기간**: 40 워킹데이 (2달)
- **목표 레벨**: 프로그래머스 Level 2 안정화 → Level 3 도전
- **플랫폼**: 프로그래머스

---

## 폴더 구조

```
coding-test-study/
├── CLAUDE.md              ← 이 파일 (Claude Code 지시파일)
├── README.md              ← 진도 대시보드
├── PROGRESS.md            ← 날짜별 체크리스트
├── .claude/
│   ├── rules/             ← Claude Code 행동 규칙
│   └── hooks/             ← 자동화 스크립트
├── theory/                ← 알고리즘 이론 정리 md
├── problems/              ← 풀이 코드 (level1~3)
└── review/                ← 오답노트
```

---

## Claude Code 행동 규칙

### 풀이 리뷰 요청 시
1. 시간복잡도 / 공간복잡도 반드시 명시
2. 개선 가능한 부분 코드로 제시
3. 관련 프로그래머스 추천 문제 1~2개 제안
4. 파일 상단에 리뷰 결과 코멘트 블록 추가

### 이론 md 생성 시
- `theory/` 폴더에 저장
- 템플릿: `.claude/rules/theory.md` 참고
- 반드시 Java 예제 코드 포함
- 시각화(표·흐름) 포함

### 진도 업데이트 시
- `PROGRESS.md` 해당 Day 체크 처리
- `README.md` 진도율 테이블 갱신
- Git 커밋 메시지 포맷: `[DayN] 완료 - {주제}`

### 오답노트 작성 시
- `review/mistake_log.md` 에 추가
- 형식: 문제명 / 틀린 이유 / 핵심 배운 점

---

## 커밋 컨벤션

```
[Day1] Java 기초 - ArrayList/HashMap 정리
[Day5] 완전탐색 - 모의고사 풀이 완료
[Review] BFS 오답노트 추가
[Theory] DP 이론 md 생성
```

---

## 주의사항

- 모든 코드는 Java 기준
- 프로그래머스 제출 형식 유지 (`Solution` 클래스, `solution` 메서드)
- 시간복잡도 분석은 Big-O 표기법 사용
