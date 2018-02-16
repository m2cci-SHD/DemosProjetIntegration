/*
 * Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package im2ag.m2pcci.theatre.ctrler;

import im2ag.m2pcci.theatre.dao.SpectacleDAO;
import im2ag.m2pcci.theatre.model.Spectacle;
import java.io.IOException;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * L'utilisateur a choisi un spectacle. récupère dans la BD les infos associées
 * à ce spectacle, et redirige vers la page permettant à l'utilisateur de choisir
 * ses places.
 * 
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
@WebServlet(name = "SpectacleCtrler", urlPatterns = {"/spectacle"})
public class SpectacleCtrler extends HttpServlet {
    @Resource(name = "jdbc/test")
    private DataSource ds;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // récupère dans la requête le numéro du spectacle sélectionné par l'utilisateur
            int nospectacle = Integer.parseInt(request.getParameter("nospectacle"));
            // récupère dans la BD les informations associées au spectacle
            Spectacle spect = SpectacleDAO.getSpectacle(ds, nospectacle);
            // stocke le spectacle en session pour qu'il puisse être utilisé lors des prochaines requêtes
            HttpSession session = request.getSession();
            session.setAttribute("spectacle", spect);
            // redirection vers la vue affichant le plan du theatre pour que l'utiisateur choisisse ses places
            request.getRequestDispatcher("/WEB-INF/achatPlaces.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex.getMessage(),ex);
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
