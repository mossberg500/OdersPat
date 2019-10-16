package ua.orders.servlets;

import ua.orders.entity.Client;
import ua.orders.entity.Good;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ua.orders.servlets.ContextListenerServlet.clientsDAOImlp;

public class ClientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String command = req.getParameter("command");
        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        String id = req.getParameter("id");

        switch (command) {
            case "All Clients":
                List<Client> clientsList = clientsDAOImlp.getAll(Client.class);
                req.setAttribute("lstClients", clientsList);
                RequestDispatcher rd1 = getServletContext().getRequestDispatcher("/viewclient.jsp");
                rd1.forward(req, resp);
                break;
            case "Add":
                if (fullName != null && phone != null) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/addclient.jsp");
                    rd.forward(req, resp);
                }
                break;
            case "Apply":
                if (id != null) {
                    clientsDAOImlp.update(new Client(Integer.parseInt(id), fullName, phone));
                    RequestDispatcher rd3 = getServletContext().getRequestDispatcher("/viewclient.jsp");
                    rd3.forward(req, resp);

                }
                break;
            case "Delete":
                if (id != null) {
                    System.out.println("id = " + id);
                    clientsDAOImlp.delete(Integer.parseInt(id));
                    RequestDispatcher rd4 = getServletContext().getRequestDispatcher("/viewclient.jsp");
                    rd4.forward(req, resp);
                }
                break;
        }
             RequestDispatcher rd2 = getServletContext().getRequestDispatcher("/index.jsp");
             rd2.forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        clientsDAOImlp.add(new Client(fullName, phone));
        List<Client> clientsList = clientsDAOImlp.getAll(Client.class);
        req.setAttribute("lstClients", clientsList);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewclient.jsp");
        rd.forward(req, resp);

    }
}