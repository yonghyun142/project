package edu.global.ex.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.global.ex.dao.BoardDao;
import edu.global.ex.dto.BoardDto;

public class BoardListCommand implements BoardCommand {
	
	//�ڼ��� �����ϹǷ� interface�� �޼ҵ带 override�Ѵ�.
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
	
		BoardDao boardDao = new BoardDao();
		
		//"select * from mvc_board order by bgroup desc,bstep asc";
		//���̺� �ִ� ��� �����͸� ���� �´ٴ� ��
		List<BoardDto> dtos = boardDao.list();
		
		request.setAttribute("list", dtos);
	}
}
