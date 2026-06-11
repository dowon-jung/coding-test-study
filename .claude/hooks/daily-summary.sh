#!/bin/bash
# daily-summary.sh
# 하루 마무리 시 실행 — 오늘 작업 요약 + 커밋 유도

echo ""
echo "╔══════════════════════════════════╗"
echo "║      📊 오늘의 스터디 요약         ║"
echo "╚══════════════════════════════════╝"
echo ""

# 오늘 수정된 파일 목록
echo "📁 오늘 작업한 파일:"
git diff --name-only HEAD 2>/dev/null || git status --short 2>/dev/null
echo ""

# 풀이 파일 개수
JAVA_COUNT=$(find ./problems -name "*.java" | wc -l | tr -d ' ')
echo "☕ 총 풀이 파일: ${JAVA_COUNT}개"

# 이론 파일 개수
THEORY_COUNT=$(find ./theory -name "*.md" | wc -l | tr -d ' ')
echo "📚 총 이론 정리: ${THEORY_COUNT}개"

# 오답노트 라인 수
if [ -f "./review/mistake_log.md" ]; then
  REVIEW_COUNT=$(grep -c "^##" ./review/mistake_log.md 2>/dev/null || echo "0")
  echo "📝 오답노트: ${REVIEW_COUNT}개"
fi

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "💬 오늘 커밋 메시지 제안:"
echo "   git add . && git commit -m \"[DayN] 완료 - {오늘 주제}\""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
