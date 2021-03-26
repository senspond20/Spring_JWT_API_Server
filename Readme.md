# 

사용자가 버튼을 눌러 요청하는 작업까지는 동일하다. 
하지만 access token을 바로 사용자에게 넘기는 것이 아니라, authorization code를 먼저 넘긴다. 
그 후 먼저 백엔드(Client)에서 authorization code를 기록하고, 
백엔드에서 Resource Server로 직접 통신하여 자신의 client id와 client secret과 authorization code를 
보내며 access token을 요청한다. 

이후 Resource Server는 3가지 값을 비교하여 일치 여부를 판단한 후 저장하고 있던 
authorization code를 삭제 후 access token을 전송한다. 
이때 authorization code를 삭제하는 이유는 재접속을 방지하기 위해서다. 
결국, Client와 Resource Server가 같은 access token을 저장하게 된다.

이 방법이 더 안전한 이유는 인터넷 브라우저를 통해 사용자 정보를 교환한다는 자체가 매우 위험한 상황이다. 
때문에 서버대 서버로 직접 통신하기 때문인 것이다. 

하나 더 부가적으로 말하면, 토큰의 만료시간이 다 되어 소멸되었을 때, 
refresh token을 사용하면 된다. refresh token은 access token과 같이 발급되는데 만약 access token이 만료된다면, 
refresh token을 이용하여 access tokend을 재발급 받을 수 있다.
