/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.servlet;

import he1.seguridades.entities.SegUsuario;
import he1.seguridades.sessions.SegUsuarioFacade;
import he1.sis.entities.Pacientes;
import he1.sis.sessions.PacientesFacade;
import he1.utilities.SesionSeguridades;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author christian_ruiz
 */
@WebServlet(name = "Decrypt", urlPatterns = {"/decr"})
public class Decrypt extends HttpServlet {

    @EJB
    private SesionSeguridades sesionSeguridades;

    @EJB
    private PacientesFacade pacientesFacade;
    Pacientes pacientes;

    @EJB
    private SegUsuarioFacade segUsuarioFacade;

    @EJB
    private SesionSeguridades encryptFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            //se tiene el id del usuario
            String usuid = "";
            String usuarioEncriptado = (request.getParameter("xphsumf"));
            usuid = segUsuarioFacade.p_decript_palabra(usuarioEncriptado);
            System.out.println("usuarioEncriptado = " + usuarioEncriptado);

            usuid = segUsuarioFacade.p_decript_palabra(usuarioEncriptado);
            SegUsuario segusuario = segUsuarioFacade.find(new BigDecimal(usuid));
            //se almacena el segusuario para que se pueda acceder desde cualquier 
            //managedbean a las propiedades del mismo
            session.setAttribute("mbsegusuario", segusuario);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
