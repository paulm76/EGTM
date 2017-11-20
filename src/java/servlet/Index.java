/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Paul
 */
@WebServlet(name = "Index", urlPatterns = {"/index"})
public class Index extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Gestion du mode utilisateur ou visiteur
        HttpSession session = request.getSession();
        String sessionKey, sessionValue;
        sessionKey = (String) session.getAttribute("EGTMUserSession");
        if (sessionKey != null){
            sessionValue = (String) request.getAttribute(sessionKey);
            Cookie cookie = new Cookie("EGTMUserCookie",sessionValue);
            cookie.setMaxAge(1000*60*30);
            response.addCookie(cookie);
        } else {
            Cookie[] cookies = request.getCookies();
            int cookiesLen = cookies.length;
            for(int i=0; i<cookiesLen; i++){
                if(cookies[i].getName().equals("EGTMUserCookie")){
                    session.setAttribute("EGTMUserSession", cookies[i].getValue());
                    cookies[i].setMaxAge(1000*60*30);
                    response.addCookie(cookies[i]);
                    i = cookiesLen;
                }
            }
        }
        boolean visitMod = true;
        session = request.getSession();
        if (session.getAttribute("EGTMUserSession") != null){
            visitMod = false;
        }
        
        
        //Recuperation des teams
        
        //
        if (visitMod){
            request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/jsp/indexUsers.jsp").forward(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
