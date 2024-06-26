package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId,String toId,int money) throws SQLException {

        Connection con = dataSource.getConnection();

        try{
            // 트랜 잭션 시작
            con.setAutoCommit(false);
            // 비즈니스 로직 시작
            bizLogic(fromId, toId, money, con);
            // 성공시 커밋
            con.commit();
        } catch (Exception e){
            // 실패시 롤백
            con.rollback();
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }

    private void bizLogic(String fromId, String toId, int money, Connection con) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);
        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId,toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

    private void release(Connection con){
        if(con != null){
            try{
                con.setAutoCommit(true); // 커넥션 풀로 돌아갈때는 트루 상태로~
                con.close();
            } catch (Exception e){
                log.info("error",e);
            }
        }
    }
}
