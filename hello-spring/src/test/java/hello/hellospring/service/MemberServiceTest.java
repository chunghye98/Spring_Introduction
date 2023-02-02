package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MemberServiceTest {

//    MemberService memberService = new MemberService(memberRepository);
//    MemoryMemberRepository repository = new MemoryMemberRepository();
    // MemoryService 안에 있는 MemoryMemberRepository와 다른 객체를 생성한 것, 같은 것을 쓰는 것이 낫다.

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        // 이렇게 하면 같은 메모리 리포지토리를 사용할 수 있다.
        // memberService 입장에서 memberRepository를 받았으므오 의존성 주입(DI)라 한다.
    }

    @AfterEach // 메서드가 끝날 때마다 동작, 콜백 메서드
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given 어떤 것이 주어졌을 때
        Member member = new Member();
        member.setName("hello");

        // when 어떤 것을 한다면
        Long saveId = memberService.join(member);

        // then 어떤 결과가 나와야 한다
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); // member2를 join 할 때 해당 예외가 발생해야 한다.
    }

    @Test
    void findOne() {
    }
}