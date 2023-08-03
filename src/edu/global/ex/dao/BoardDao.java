package edu.global.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import edu.global.ex.dto.BoardDto;



public class BoardDao {
   private DataSource datasource = null;
   
   public BoardDao() {
      try {
         Context context = new InitialContext();
         datasource = (DataSource) context.lookup("java:comp/env/jdbc/oracle");

      } catch (Exception e) {
         // TODO: handle exception
         e.printStackTrace();
      }
   }
   
   /*
      리턴타입이 BDto인 이유 : 글목록을 다 표시하려면 ArrayList를 써줘야하지만, 글하나의 내용만 
      표시하려면 테이블의 한 행만 가져오면 되므로 BDto로 타입을 지정하였다.

      파라미터가 String bid만 있는 이유 : list.jsp에서 contentView.do로 넘어갈때 참조태그에 
      ${dto.bid}가 넘어가므로 bid를 받아줘야한다.
   */
   
   public BoardDto contentView(String bid) {

      BoardDto dto = null;

      Connection con = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      try {
         //쿼리문 작성시 찾으려는 글번호에 ?로 넘기고 아래서 preparedStatement로 set해준다.
         
         String query = "select * from mvc_board where bid = ?"; // (가지고 오고자하는 쿼리문을 넣어준다)
      
         con = datasource.getConnection();
         stmt = con.prepareStatement(query);
         
          //int형으로 setInt해야하는데, bid의 자료형은 String이므로 Integer로 바꿔준다.
         stmt.setInt(1, Integer.valueOf(bid));
         
         rs = stmt.executeQuery();

           //한 행의 데이터만 가져오기 때문에 while 말고 if(rs.next())로 해도 가능하다.      
         while (rs.next()) {
            
            int id = rs.getInt("bid");
            String bname = rs.getString("bname");
            String btitle = rs.getString("btitle");
            String bcontent = rs.getString("bcontent");
            Timestamp bdate = rs.getTimestamp("bdate");
            int bhit = rs.getInt("bhit");
            int bgroup = rs.getInt("bgroup");
            int bstep = rs.getInt("bstep");
            int bindent = rs.getInt("bindent"); 
            
            dto = new BoardDto(id, bname, btitle, bcontent, bdate, bhit, bgroup, bstep, bindent);
            

         }

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         // ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
         // 열었으므로 거꾸로 닫아준다.
         try {
            if (rs != null)
               rs.close();
            if (stmt != null)
               stmt.close();
            if (con != null)
               con.close();

         } catch (Exception e2) {
            e2.printStackTrace();
         }
      }
      //글 내용 하나는 테이블의 한 행만 불러오게 되므로 BDto의 객체를 리턴 한다.
      return dto;

   }   
   
   public List<BoardDto> list() {

      ArrayList<BoardDto> dtos = new ArrayList<BoardDto>();

      Connection con = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      try {
         String query = "select * from mvc_board order by bgroup desc,bstep asc"; // (가지고 오고자하는 쿼리문을 넣어준다)
      
         con = datasource.getConnection();
         stmt = con.prepareStatement(query);
         rs = stmt.executeQuery();

         // 반복문을 사용해서 ArrayList에 가져온 데이터를 집어넣는다.
         while (rs.next()) {
            
            int bid = rs.getInt("bid");
            String bname = rs.getString("bname");
            String btitle = rs.getString("btitle");
            String bcontent = rs.getString("bcontent");
            Timestamp bdate = rs.getTimestamp("bdate");
            int bhit = rs.getInt("bhit");
            int bgroup = rs.getInt("bgroup");
            int bstep = rs.getInt("bstep");
            int bindent = rs.getInt("bindent"); 
            
            BoardDto dto = new BoardDto(bid, bname, btitle, bcontent, bdate, bhit, bgroup, bstep, bindent);

            dtos.add(dto);

         }

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         // ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
         // 열었으므로 거꾸로 닫아준다.
         try {
            if (rs != null)
               rs.close();
            if (stmt != null)
               stmt.close();
            if (con != null)
               con.close();

         } catch (Exception e2) {
            e2.printStackTrace();
         }
      }

      return dtos;

   }

   public void write(String bname, String btitle, String bcontent) {
      
      System.out.println("write()...");
      Connection con = null;
      PreparedStatement stmt = null;


      try {
    	  String query = "insert into mvc_board "
                  + "(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent)"
                  + "values (mvc_board_seq.nextval,?,?,?,0, mvc_board_seq.currval,0,0)";
            
      
         con = datasource.getConnection();
         stmt = con.prepareStatement(query);
         
         stmt.setString(1,  bname);
         stmt.setString(2,  btitle);
         stmt.setString(3,  bcontent);
         
         int rn = stmt.executeUpdate();
         
         System.out.println("write 한 갯수" + rn);
         

         // 반복문을 사용해서 ArrayList에 가져온 데이터를 집어넣는다.

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         // ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
         // 열었으므로 거꾸로 닫아준다.
         try {

            if (stmt != null)
               stmt.close();
            if (con != null)
               con.close();

         } catch (Exception e2) {
            e2.printStackTrace();
         }
      }
   }   
   public void modify(String bid, String bname, String btitle, String bcontent) {
	      
	      System.out.println("modify()...");
	      Connection con = null;
	      PreparedStatement stmt = null;


	      try {
	    	  String query = "update mvc_board "
	                  + " set bname=?, btitle=?, bcontent=? where bid=?";
	    	  			
	      
	         con = datasource.getConnection();
	         stmt = con.prepareStatement(query);
	         
	         
	         stmt.setString(1,  bname);
	         stmt.setString(2,  btitle);
	         stmt.setString(3,  bcontent);
	         stmt.setInt(4,  Integer.valueOf(bid));
	         
	         int rn = stmt.executeUpdate();
	         
	         System.out.println("write 한 갯수" + rn);
	         

	         // 반복문을 사용해서 ArrayList에 가져온 데이터를 집어넣는다.

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         // ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
	         // 열었으므로 거꾸로 닫아준다.
	         try {

	            if (stmt != null)
	               stmt.close();
	            if (con != null)
	               con.close();

	         } catch (Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	   }   
   public void delete(String bid) {
	      
	      System.out.println("delete()...");
	      Connection con = null;
	      PreparedStatement stmt = null;


	      try {
	    	  String query = "delete from mvc_board where bid=? ";
	               
	            
	      
	         con = datasource.getConnection();
	         stmt = con.prepareStatement(query);
	         
	         stmt.setInt(1,  Integer.valueOf(bid));
	       
	         
	         int rn = stmt.executeUpdate();
	         
	         System.out.println("write 한 갯수" + rn);
	         

	         // 반복문을 사용해서 ArrayList에 가져온 데이터를 집어넣는다.

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         // ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
	         // 열었으므로 거꾸로 닫아준다.
	         try {

	            if (stmt != null)
	               stmt.close();
	            if (con != null)
	               con.close();

	         } catch (Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	   }  
   public BoardDto reply_view(String bid) {

	      BoardDto dto = null;

	      Connection con = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;

	      try {
	         //쿼리문 작성시 찾으려는 글번호에 ?로 넘기고 아래서 preparedStatement로 set해준다.
	         
	         String query = "select * from mvc_board where bid = ?"; // (가지고 오고자하는 쿼리문을 넣어준다)
	      
	         con = datasource.getConnection();
	         stmt = con.prepareStatement(query);
	         
	          //int형으로 setInt해야하는데, bid의 자료형은 String이므로 Integer로 바꿔준다.
	         stmt.setInt(1, Integer.valueOf(bid));
	         
	         rs = stmt.executeQuery();

	           //한 행의 데이터만 가져오기 때문에 while 말고 if(rs.next())로 해도 가능하다.      
	         while (rs.next()) {
	            
	            int id = rs.getInt("bid");
	            String bname = rs.getString("bname");
	            String btitle = rs.getString("btitle");
	            String bcontent = rs.getString("bcontent");
	            Timestamp bdate = rs.getTimestamp("bdate");
	            int bhit = rs.getInt("bhit");
	            int bgroup = rs.getInt("bgroup");
	            int bstep = rs.getInt("bstep");
	            int bindent = rs.getInt("bindent"); 
	            
	            dto = new BoardDto(id, bname, btitle, bcontent, bdate, bhit, bgroup, bstep, bindent);
	            

	         }

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         // ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
	         // 열었으므로 거꾸로 닫아준다.
	         try {
	            if (rs != null)
	               rs.close();
	            if (stmt != null)
	               stmt.close();
	            if (con != null)
	               con.close();

	         } catch (Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	      //글 내용 하나는 테이블의 한 행만 불러오게 되므로 BDto의 객체를 리턴 한다.
	      return dto;

	   }   
   public void reply(String bid, String bname, String btitle, String bcontent,
	         String bgroup, String bstep, String bindent) {
	      
	      System.out.println("reply()...");
	      
	      replyShape(bgroup, bstep);
	      
	      Connection con = null;
	      PreparedStatement stmt = null;


	      
	      try {
	    	  String query = "insert into mvc_board(bid,bname,btitle,bcontent, " +
                      " bgroup,bstep,bindent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
	                  
	            
	      
	         con = datasource.getConnection();
	         stmt = con.prepareStatement(query);
	         
	         stmt.setString(1, bname);
	         stmt.setString(2, btitle);
	         stmt.setString(3, bcontent);
	         stmt.setInt(4, Integer.parseInt(bgroup));
	         stmt.setInt(5, Integer.parseInt(bstep)+1);
	         stmt.setInt(6, Integer.parseInt(bindent)+1);
	        
	         
	         int rn = stmt.executeUpdate();
	         
	         System.out.println("write 한 갯수" + rn);
	         

	         // 반복문을 사용해서 ArrayList에 가져온 데이터를 집어넣는다.

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         // ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
	         // 열었으므로 거꾸로 닫아준다.
	         try {

	            if (stmt != null)
	               stmt.close();
	            if (con != null)
	               con.close();

	         } catch (Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	   }   
   private void replyShape(String group, String step) {

	      System.out.println("replyShape()..."); // -> 디버깅문구

	      Connection connection = null;
	      PreparedStatement preparedStatement = null;

	      try {
	         String query = "update mvc_board set bstep = bstep + 1 " + "where bgroup = ? and bstep > ?";
	         /*
	          * ※bstep을 1씩 증가 시킨다는 건 -> 답글달려는 원본글 기준 답글 달린 글들이 1칸씩 아래로 내려간다는 뜻.
	          */

	         preparedStatement = connection.prepareStatement(query);

	         preparedStatement.setInt(1, Integer.parseInt(group));
	         preparedStatement.setInt(2, Integer.parseInt(step));

	         int rn = preparedStatement.executeUpdate();
	         System.out.println("업데이트 갯수 : " + rn);

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	            if (connection != null)
	               connection.close();
	         } catch (Exception e2) {
	            e2.printStackTrace();
	         }
	      }

	   }

   
}