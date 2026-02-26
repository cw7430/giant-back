-- 1. 테이블 삭제 (CASCADE로 연관된 제약조건/인덱스 일괄 삭제)
DROP TABLE IF EXISTS "employee"."serial" CASCADE;
DROP TABLE IF EXISTS "employee"."profile" CASCADE;
DROP TABLE IF EXISTS "employee"."team" CASCADE;
DROP TABLE IF EXISTS "employee"."position" CASCADE;
DROP TABLE IF EXISTS "employee"."department" CASCADE;
DROP TABLE IF EXISTS "auth"."refresh_token" CASCADE;
DROP TABLE IF EXISTS "auth"."account" CASCADE;

-- 2. 커스텀 타입(ENUM) 삭제
DROP TYPE IF EXISTS "employee"."employee_role";
DROP TYPE IF EXISTS "auth"."auth_role";

-- 3. 스키마 삭제
DROP SCHEMA IF EXISTS "employee" CASCADE;
DROP SCHEMA IF EXISTS "auth" CASCADE;