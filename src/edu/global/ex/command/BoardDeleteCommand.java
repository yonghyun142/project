package edu.global.ex.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.global.ex.dao.BoardDao;
import edu.global.ex.dto.BoardDto;

public class BoardDeleteCommand implements BoardCommand {
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
	
		System.out.println("BoardDeleteCommand entry.."); //디버깅을 위한 코드
		
		String bid = request.getParameter("bid");
		
		
		
		BoardDao boardDao = new BoardDao();
		
		
		boardDao.delete(bid);
		
		
	}
}
