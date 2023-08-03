package edu.global.ex.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.global.ex.dao.BoardDao;
import edu.global.ex.dto.BoardDto;

public class BoardModifyCommand implements BoardCommand {
	
	//�ڼ��� �����ϹǷ� interface�� �޼ҵ带 override�Ѵ�.
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
	
		System.out.println("BoardModifyCommand entry.."); //디버깅을 위한 코드
		
		String bid = request.getParameter("bid");
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		
		
		
		BoardDao boardDao = new BoardDao();
		
		
		boardDao.modify(bid,bname,btitle,bcontent);
		
		
	}
}
