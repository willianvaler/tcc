/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wav.tcc.entities.Belief;
import wav.tcc.entities.ConfidenceClassStudent;
import wav.tcc.net.StudentConfidenceBayesianNet;
import wav.tcc.net.StudentEffortBayesianNet;
import wav.tcc.transactions.EvaluationDataTransactions;

/**
 *
 * @author willi
 */
@WebServlet(name = "ClassServlet", urlPatterns =
{
    "/ClassServlet"
})
public class ClassServlet extends HttpServlet
{

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
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentNets</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentNets at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
            throws ServletException, IOException
    {
        String id = request.getParameter( "ClassId" );
        
        List<ConfidenceClassStudent> students = new EvaluationDataTransactions().loadReadyStudentData( Integer.valueOf( id ) );
        
        List<Object> data = new ArrayList();
        
        if ( students != null && !students.isEmpty() ) 
        {
            List<Object[]> confidenceBeliefs = new StudentConfidenceBayesianNet().loadConfidenceBayesNet( students );
            List<Object[]> effortBeliefs     = new StudentEffortBayesianNet().loadEffortBayesNet( students );

            for( int i = 0; i < confidenceBeliefs.size(); i++ )
            {
                float[] confidence = (float[]) confidenceBeliefs.get( i )[0];
                float[] effort     = (float[]) effortBeliefs.get( i )[0];
                String name1       = (String)confidenceBeliefs.get(i)[1];
                String name2       = (String)effortBeliefs.get(i)[1];
                
                if (name1.equals(name2)) 
                {
                    data.add( new Object[]{ confidence[0], effort[0], name1 } );
                }
                
                else
                {
                    System.out.println("problema no order");
                }
            }
        }
        
        else
        {
            data.add( new float[]{-1, -1});
        }
        
        
        Gson gson = new Gson();
        String json = gson.toJson( data );

        response.setContentType( "application/json" );
        response.setCharacterEncoding( "UTF-8" );
        response.getWriter().write( json );
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }

    
}
