package nts.ntssearch.repository;

import nts.ntssearch.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.io.CharArrayReader;
import java.io.Reader;
import java.sql.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name, user_id, password) values(?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getUser_id());
            pstmt.setString(3, member.getPassword());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            conn.close();
            pstmt.close();
            rs.close();
            return member;
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Member> findByUserId(String user_id) {
        String sql = "select * from member where user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                conn.close();
                pstmt.close();
                rs.close();
                return Optional.of(member);
            } else {
                conn.close();
                pstmt.close();
                rs.close();
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String findForLogin(String user_id, String password) {
        String sql = "select * from member where user_id = ? and password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_id);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();


            if(rs.next()){
                return "login success";
            }else{
                return "login fail";
            }
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }


}
