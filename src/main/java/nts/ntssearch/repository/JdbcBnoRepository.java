package nts.ntssearch.repository;

import nts.ntssearch.domain.closedNum;
import nts.ntssearch.domain.presentNum;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcBnoRepository implements BnoRepository{
    private final DataSource dataSource;

    public JdbcBnoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }


    @Override
    public void savePresent(String b_no) {
        String sql = "insert into presentnum(business_num) values(?)";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, b_no);

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            conn.close();
            pstmt.close();
            rs.close();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveClosed(String b_no) {
        String sql = "insert into closednum(business_num) values(?)";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, b_no);

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            conn.close();
            pstmt.close();
            rs.close();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<presentNum> findPresent() {
        String sql = "select * from presentnum";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<presentNum> presentNums = new ArrayList<>();
            while(rs.next()) {
                presentNum pn = new presentNum();
                pn.setId(rs.getLong("id"));
                pn.setBusiness_num(rs.getString("business_num"));
                presentNums.add(pn);
            }
            return presentNums;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<closedNum> findClosed() {
        String sql = "select * from closednum";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<closedNum> closedNums = new ArrayList<>();
            while(rs.next()) {
                closedNum cn = new closedNum();
                cn.setId(rs.getLong("id"));
                cn.setBusiness_num(rs.getString("business_num"));
                closedNums.add(cn);
            }
            return closedNums;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
