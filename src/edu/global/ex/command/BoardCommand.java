package edu.global.ex.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoardCommand {
	
	//�������̽��Ƿ� �޼ҵ弱�� �����ϴ�. ������ �ڼ��� �Ѵ�. 
	void execute(HttpServletRequest request, HttpServletResponse response);
}
