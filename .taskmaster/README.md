# Taskmaster AI MCP 사용 가이드

Taskmaster AI는 프로젝트 관리를 위한 AI 기반 도구입니다. MCP(Model Context Protocol)를 통해 AI 어시스턴트와 상호작용하여 프로젝트 태스크를 관리할 수 있습니다.

## 기본 명령어

- `taskmaster list` - 모든 태스크 목록 표시
- `taskmaster add <제목> --description <설명> --priority <우선순위>` - 새 태스크 추가
- `taskmaster update <태스크ID> --status <상태> --priority <우선순위>` - 태스크 업데이트
- `taskmaster remove <태스크ID>` - 태스크 삭제
- `taskmaster info <태스크ID>` - 태스크 상세 정보 표시
- `taskmaster summary` - 프로젝트 태스크 요약 정보 표시

## 상태 값

- `pending` - 대기 중
- `in_progress` - 진행 중
- `completed` - 완료됨
- `blocked` - 차단됨

## 우선순위 값

- `low` - 낮음
- `medium` - 중간
- `high` - 높음
- `critical` - 중요

## 예제

```
taskmaster add "API 엔드포인트 구현" --description "사용자 인증 API 엔드포인트 구현" --priority high
taskmaster update task-004 --status in_progress
taskmaster list --filter status=in_progress
```
