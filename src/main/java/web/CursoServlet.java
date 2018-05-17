package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CursoServlet", urlPatterns = {"/cursoservlet"})
public class CursoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        
        int idCurso = 1; idCurso++;
        String nomeCurso = req.getParameter("nome-curso");
        String turnoCurso = req.getParameter("turno-curso");
        String qtdEstudantes = req.getParameter("qtd-estudantes");
        Date agora = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String dataCadastro = f.format(agora);

        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/erpcolegio", "erpcolegio", "12345");
            PreparedStatement p = connection.prepareStatement
            ("INSERT INTO CURSOS(CURSO_ID, NOME_CURSO, TURNO_CURSO, QTD_ESTUDANTES, DATA_CADASTRO) VALUES(?, ?, ?, ?, ?)");
            
            p.setInt(1, idCurso);
            p.setString(2, nomeCurso);
            p.setString(3, turnoCurso);
            p.setInt(4, Integer.parseInt(qtdEstudantes));
            p.setString(5, dataCadastro);
            
            p.execute();
           
            PrintWriter saida = resp.getWriter();
            saida.println("Concluido!");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
////////////////////////////DO GET/////////////////////////////////////////////////

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Prepara response
        response.setContentType("text/html");
        PrintWriter saida = response.getWriter();
        //Busca dados no Derby/Firebird
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/erpcolegio", "erpcolegio", "123456");

            PreparedStatement p = connection.prepareStatement("select * from empresa");

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                saida.println("->" + rs.getString("nome"));
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

}
