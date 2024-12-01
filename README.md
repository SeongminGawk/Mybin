주요 기능
1. 회원 관리
회원가입:

새로운 사용자가 회원 정보를 입력하면 데이터베이스에 안전하게 저장됩니다.
비밀번호는 해시와 솔트를 사용해 암호화하여 저장.
관련 코드:
MemberRegisterService.java: 회원가입 로직 처리.
MemberRepository.java: 데이터베이스와 연동.
로그인:

사용자의 아이디와 비밀번호를 검증하여 인증 처리.
Spring Security를 통해 세션 기반 인증 구현.
관련 코드:
MemberLoginService.java: 로그인 로직 처리.
SecurityConfig.java: 인증 및 권한 설정.
2. 비밀번호 찾기
비밀번호 복구:
사용자가 등록한 이메일이나 전화번호로 비밀번호 복구 기능 제공.
랜덤 인증번호를 생성하고, 성공적으로 인증하면 새 비밀번호를 설정 가능.
관련 코드:
FindInfoService.java: 이메일 인증과 비밀번호 업데이트 처리.
3. 주차장 등록 및 조회
주차장 등록:

사용자가 주차장 이름, 위치 등을 입력하면 지도에 등록.
Kakao Maps API를 활용하여 위치를 시각적으로 표시.
관련 코드:
ParkingController.java: 주차장 정보 등록 및 처리.
ParkingRepository.java: 데이터베이스와 연동.
주차장 조회:

사용자가 주차장을 지도에서 선택하면 세부 정보를 확인 가능.
관련 코드:
MapController.java: 지도 및 주차장 데이터 조회.
4. 보안 및 데이터 암호화
AES/RSA 암호화:

사용자의 민감한 데이터를 안전하게 보호.
비밀번호는 SHA-256 해시로 암호화.
관련 코드:
FindInfoService.java: 암호화 및 복호화 처리.
Spring Security:

사용자 인증 및 권한 부여.
관련 코드:
SecurityConfig.java: 보안 설정.
5. 반응형 UI
HTML5, CSS3, JavaScript:
반응형 디자인을 통해 모바일 및 데스크톱 환경 모두에서 최적화된 UI 제공.
관련 파일:
register.html, map.html: 주요 화면 레이아웃.
register.css, map.css: 스타일링.
map.js: 지도 및 동적 기능 처리.
