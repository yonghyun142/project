package edu.global.ex.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.global.ex.dao.BoardDao;
import edu.global.ex.dto.BoardDto;

public class BoardReplyCommand implements BoardCommand {
	
	//�ڼ��� �����ϹǷ� interface�� �޼ҵ带 override�Ѵ�.
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
	
		System.out.println("BoardReplyCommand entry.."); //디버깅을 위한 코드
		
	      String bname = request.getParameter("bname");
	      String btitle = request.getParameter("btitle");
	      String bcontent = request.getParameter("bcontent");
	      
	      String bid = request.getParameter("bid");
	      String bgroup = request.getParameter("bgroup");
	      String bstep = request.getParameter("bstep");
	      String bindent = request.getParameter("bindent");
		
		
		
		BoardDao boardDao = new BoardDao();
		
		
		boardDao.reply(bid, bname, btitle, bcontent, bgroup, bstep, bindent);
	
	}
}
