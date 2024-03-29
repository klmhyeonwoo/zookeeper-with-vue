[//]: # (
Copyright 2020 AhnLab, Inc. All rights reserved.
Any part of this source code can not be copied with any method without
prior written permission from the author or authorized person.)

# Zookeeper UI

- Zookeeper UI for client

---

```shell
# 프로젝트 초기 세팅
pnpm install

# 프로젝트 실행
pnpm dev

# 프로젝트 빌드
pnpm build

# 프로젝트 프리뷰
pnpm preview
```

---

## 개발 환경

### 런타임

- `node` : 17.9.1

### 빌드 및 패키지 매니저
- `vite` : ^5.1.5
- `pnpm` : 8.14.3

### 자바스크립트 슈퍼셋
- `typescript` : ^~5.4.0

### 프론트엔드 프레임워크

- `vue` : ^3.4.21

    - `vue-router` : ^4.3.0
    - `pinia` : ^2.1.7

### 기타 패키지

- `axios` : ^1.6.8
- `@tanstack/vue-query` : ^5.28.9
- `vue-svg-loader` : ^0.16.0

---

## 코드 컨벤션

- ESLInt Rule : Vue3 Essential
- Editor Config

| 룰             | 설정 값        |
|---------------|-------------|  
| printWidth    | 2 spaces    |
| tabWidth      | 2           |
| semi          | true        |
| useTabs       | false       |
| singleQuote   | false       |
| quoteProps    | "as-needed" |
| jsxSingleQuote | false       |
| trailingComma | "all"       |
| bracketSpacing | true        |
| bracketSameLine | false       |
| arrowParens   | "always"    |,
|requirePragma| false       |,
|proseWrap| "preserve"|,
|endOfLine| "lf"|,
|singleAttributePerLine| false|,
|insertPragma| false|,
|htmlWhitespaceSensitivity| "css"|,
|embeddedLanguageFormatting| "auto"|,
|vueIndentScriptAndStyle| false|,


## 네이밍 룰

### 컴포넌트

- 컴포넌트 정의
    - Pascal Case
      > MyComponent.vue
- 컴포넌트 호출 (템플릿 영역)
    - Pascal Case
  > `<MyComponent />`

### 폴더명

- Camel Case
  > threatActor
### 자바스크립트 파일명

- Camel Case
  > domUtil.js
- composables 파일명
    - Camel Case
      > useCsvDownload.js
### 변수명

- Camel Case

  ```javascript
  const storedToken = ref('');
  ```

### 메서드 (함수) 명

- Camel Case

  ```javascript
  const onClickAsecNotesDetail = async () => ({ ... })
  ```

---