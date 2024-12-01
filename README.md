## 주요 기능
### 1. 회원 관리
회원가입:

사용자가 회원 정보를 입력하면 데이터베이스에 안전하게 저장.
비밀번호는 해시와 솔트를 사용해 암호화하여 저장.
관련 코드:
Membership_join.java: 회원가입 기능을 처리.
RetrofitAPI.java, RetrofitService.java: 서버와 통신을 통해 회원 정보 전송.
로그인:

사용자가 입력한 아이디와 비밀번호를 데이터베이스와 비교하여 인증.
관련 코드:
LoginScreen.java: 로그인 화면 구현.
RetrofitAPI.java, RetrofitService.java: 서버와 통신을 통해 로그인 정보 확인.
비밀번호 변경:

사용자가 기존 비밀번호를 인증한 후 새 비밀번호로 변경.
비밀번호 변경 시 암호화 및 데이터베이스 업데이트.
관련 코드:
MemberRepository.java: 사용자 비밀번호 데이터베이스 처리.
FindInfoService.java: 비밀번호 암호화 및 업데이트 로직.
2. 지도 기능
쓰레기통 위치 조회:
사용자의 현재 위치를 기준으로 가까운 쓰레기통의 위치를 지도에 표시.
Google Maps API를 활용.
관련 코드:
MapsActivity.java: 지도와 쓰레기통 위치 표시 로직.
trash_can.csv: 쓰레기통 위치 데이터 파일.
3. 환경 팁 제공
환경 정보와 팁 제공:
사용자가 환경 보호와 분리수거에 대해 알 수 있는 유용한 팁 제공.
프래그먼트를 통해 동적 컨텐츠를 관리.
관련 코드:
TipsScreen.java: 환경 팁 화면 로직.
environmentFragment.java: 환경 관련 프래그먼트 관리.
fragment_environment.xml: 환경 프래그먼트 레이아웃.
4. 랭킹 시스템
사용자 활동 랭킹:
사용자의 활동 데이터를 기반으로 랭킹을 계산하여 표시.
관련 코드:
RankingScreen.java: 랭킹 화면 구현.
RetrofitAPI.java, RetrofitService.java: 서버로부터 랭킹 데이터 수신.
5. 감정 표현 기능
이모티콘 사용:
사용자가 분리수거 등의 활동을 할 때 감정을 이모티콘으로 표현.
관련 코드:
emoticon.java: 감정 표현 로직.
activity_emoticon.xml: 감정 표현 화면 레이아웃.
6. 데이터 바인딩 및 UI
UI 연결:
데이터 바인딩을 활용해 화면과 데이터를 연결.
주요 레이아웃 파일:
activity_home_screen.xml: 홈 화면.
activity_login_screen.xml: 로그인 화면.
activity_membership_join.xml: 회원가입 화면.
7. 네트워크 보안 및 설정
보안 설정:
네트워크 통신 보안을 위한 설정.
관련 파일:
network_security_config.xml: 네트워크 보안 규칙.
backup_rules.xml: 데이터 백업 규칙.
