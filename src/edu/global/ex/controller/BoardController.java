package edu.global.ex.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.global.ex.command.BoardCommand;
import edu.global.ex.command.BoardContentCommand;
import edu.global.ex.command.BoardDeleteCommand;
import edu.global.ex.command.BoardListCommand;
import edu.global.ex.command.BoardModifyCommand;
import edu.global.ex.command.BoardReplyCommand;
import edu.global.ex.command.BoardReplyViewCommand;
import edu.global.ex.command.BoardWriteCommand;


@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doGet() ..");
		actionDo(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("doPost() ..");
		actionDo(request,response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("actionDo() ..");
		
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		BoardCommand command = null;
		
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
   
		if(com.equals("/list.do")) {
		
			
			
			command = new BoardListCommand();
			command.execute(request, response);
			
			
			viewPage = "list.jsp";
		}
		else if(com.equals("/content_view.do")) {
			
			command = new BoardContentCommand();
			command.execute(request, response);
			
		
			viewPage = "content_view.jsp";
		}else if(com.equals("/write_view.do")) {

			viewPage = "write_view.jsp";
		}else if(com.equals("/write.do")) {

			command = new BoardWriteCommand();
			command.execute(request, response);
			
			viewPage = "list.do";
		}else if(com.equals("/modify.do")) {

			command = new BoardModifyCommand();
			command.execute(request, response);

			viewPage = "list.do";
		}else if(com.equals("/delete.do")) {

			command = new BoardDeleteCommand();
			command.execute(request, response);

			viewPage = "list.do";
		}else if(com.equals("/reply_view.do")) {

			command = new BoardReplyViewCommand();
			command.execute(request, response);

			viewPage = "reply_view.jsp";
		}else if(com.equals("/reply.do")) {

			command = new BoardReplyCommand();
			command.execute(request, response);

			viewPage = "list.do";
		}
		
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
		
		
	}


}
