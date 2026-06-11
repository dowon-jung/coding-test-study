#!/bin/bash
# post-save.sh
# Java 파일 저장 시 자동 실행 — 컴파일 체크

FILE=$1
EXT="${FILE##*.}"

if [ "$EXT" = "java" ]; then
  echo "🔍 Java 파일 감지: $FILE"
  echo "📦 컴파일 체크 중..."

  javac "$FILE" 2>&1
  if [ $? -eq 0 ]; then
    echo "✅ 컴파일 성공"
    # 컴파일 성공 시 .class 파일 정리
    rm -f "${FILE%.java}.class"
  else
    echo "❌ 컴파일 에러 — 위 오류 확인"
  fi
fi

if [ "$EXT" = "md" ]; then
  echo "📝 MD 파일 저장됨: $FILE"
  echo "💡 Git 커밋 잊지 마세요!"
fi
