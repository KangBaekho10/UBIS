![header](https://capsule-render.vercel.app/api?type=waving&text=UBIS&height=200&color=73C2FB&animation=fadeIn&fontColor=003153)

# 🖥 UBIS 🖥
### DL게임을 온라인에서 파는 쇼핑몰입니다.  

---

## 🔷 목차 🔷
### <p align="left"><a href="#-프로그램-구조">🌃 프로그램 구조 </a></p>
### <p align="left"><a href="#-시도해본-것">🪐 시도해본 것 </a></p>

---
## 🌃 프로그램 구조

<a href="https://www.notion.so/teamsparta/3-a22bf4cefb5b4e6087e59e9dfe32330f" target="_blank" >
상세한 프로그램 구조는 해당 노션에서 확인 바랍니다.</a>


# <p align="right"><a href="#-목차-">🔝</a></p>

---
## 🪐 시도해본 것

### 👾 OrderService 
<details>
<summary> 고객과 사업자 권한에 따라 로직이 분기처리 됩니다. </summary>

+ #### <code>fun getOrderList(): List<OrderResponse></code>

    
    ```kotlin
        when(memberService.getMember().role) {
            Role.CUSTOMER -> {
                val memberName = memberService.getMember().name
                orders = orderRepository.findAllByMemberId(memberId)
                    .map { it.toOrderResponse(memberName) }
                    .toMutableList()
            }

            Role.BUSINESS -> {
                orders = orderRepository.findOrderList(memberId).map {
                    it.first!!.toOrderResponse(it.second!!)
                }.toMutableList()
            }

    
    ```
    
</details>

<details>
<summary> 여러 테이블을 복합적으로 불러와 조회해야 할때, 최적화된 쿼리를 사용합니다. </summary>

+ #### <code>override fun findOrderList(memberId: Long): MutableList<Pair<Order?,String?>></code>
    
    ```kotlin
       val whereClause = BooleanBuilder()

        whereClause.and(product.memberId.eq(memberId))//사업자의 상품으로 추려냄

        val contents = queryFactory
            .select(order, member.name)
            .from(order)
            .where(whereClause)
            .join(member) // 연관 관계와 무관한 JOIN
            .on(order.memberId.eq(member.id)) // 오더의 멤버 ID와 멤버의 멤버 ID가 일치
            .fetch()

        return contents.map{
            it.get(order) to it.get(member.name)
        }.toMutableList()
    ```
    
    </details>


### 👾 OrderService 
<details>
<summary> 카카오, 네이버 소셜로그인이 가능합니다.(고객 한정) </summary>

+ #### <code>OAuthService()</code>
    
    ```kotlin
     private val kakaoOAuthClient: KakaoOAuthClient,
     private val naverOAuthClient: NaverOAuthClient,
     private val memberRepository: MemberRepository,
     private val jwtPlugin: JwtPlugin
    ```
    
    </details>


### 👾 JwtAuthenticationFilter 
<details>
<summary> JWT 토큰이 없는 경우 바로 응답을 보냅니다. </summary>

+ #### <code>override fun doFilterInternal(...)</code>
    
    ```kotlin
     else { // 토큰이 없으면

            if (request.method != "GET") {
                if (!(request.method == "POST" && request.requestURI.startsWith("/members"))) {
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.contentType = MediaType.APPLICATION_JSON_VALUE

                    jacksonObjectMapper().writeValue(response.writer, "No token")
                }
            }
        }
    ```

</details>

### 👾 MemberService 
<details>
<summary> 토큰 발행시, JoinType(소셜, 일반)로그인을 구별하고, 해당 정보를 이용해 로직을 분기처리합니다. </summary>

+ #### <code>fun updateMember(UpdateMemberRequest): MemberResponse</code>
    
    ```kotlin
      val memberId = getMemberIdFromToken()

        if (getMemberJoinTypeFromToken() == "SOCIAL") {
            throw IllegalStateException("소셜 회원은 회원정보를 수정할 수 없습니다.")
        }
    ```
    

</details>

<details>
<summary> 비밀번호 업데이트시 최근 3개의 비밀번호로는 바꾸지 못하도록 하였습니다. </summary>

+ #### <code>fun updateMember(UpdateMemberRequest): MemberResponse</code>

    
    ```kotlin
      if (updateMemberRequest.password != null) {
            // 이전에 사용했던 비밀번호 이력을 가져옴
            val pwHistory = member.pwHistory.split(",").toMutableList() // [hh23,  dddd,  1234]

            // 수정 요청한 비밀번호가 이전에 사용했던 적이 있는지 확인
            val isExistPassword = pwHistory.filter { passwordEncoder.matches(updateMemberRequest.password, it) }.size

            if (isExistPassword > 0) {
                throw ReusedPasswordException("이전에 사용했던 비밀번호는 사용할 수 없습니다.")
            }

            // 일치하는 비밀번호가 없으면 기존 변경 이력횟수를 확인
            if (pwHistory.size >= 3) { // 3번 이상이면
                // 첫 번째 요소(가장 오래 전에 사용했던 비밀번호)를 제거
                pwHistory.removeFirst()
            }

            pwHistory.add(passwordEncoder.encode(updateMemberRequest.password))

            // 변경된 비밀번호 List를 비밀번호 이력에 추가
            member.pwHistory = pwHistory.joinToString(",")
            member.password = pwHistory.last()
        }
    ```

</details>


# <p align="right"><a href="#-목차-">🔝</a></p>

---
