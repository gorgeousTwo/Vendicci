package com.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//!MyBatis Version

public class memberA {
    private Connection conn = null;
    private String stringSql = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private int resultCode = 0;

    public memberA() {initialize();}

    public int insertMember(memberT memberT) {
        initialize();
        stringSql = "INSERT INTO MEMBER VALUES(ID =?,PWD=?,PHONE=?,EMAIL=?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(stringSql);
            pstmt.setString(1, memberT.getId());
            pstmt.setString(2, memberT.getPwd());
            pstmt.setString(3, memberT.getPhone());
            pstmt.setString(4, memberT.getEmail());
            pstmt.executeUpdate();

            resultCode = 1;
            return resultCode;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            getClose(pstmt,conn);
        }
        return resultCode;
    }

    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jdbc/oracle");
        return ds.getConnection();
    }
    private void initialize() {
        conn = null;
        stringSql = null;
        pstmt = null;
        rs = null;
        resultCode = 0;
    }
    private void getClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void getClose(PreparedStatement pstmt, Connection conn) {
        if (pstmt != null) {
            try {
                pstmt.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
