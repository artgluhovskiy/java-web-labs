package org.art.web.labs.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.labs.web.AsyncRequestProcessor;
import org.art.web.labs.web.listeners.AppAsyncListener;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Simple asynchronous servlet code example.
 */
@WebServlet(urlPatterns = {"/async.call"}, asyncSupported = true)
public class AsynchronousServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(AsynchronousServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        LOG.info("AsynchronousServlet: doGet. Thread: " + Thread.currentThread().getName() + ", id: " + Thread.currentThread().getId());
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        String time = req.getParameter("time");
        int secs = Integer.valueOf(time);
        AsyncContext asyncContext = req.startAsync();
        asyncContext.addListener(new AppAsyncListener());
        ThreadPoolExecutor executor = (ThreadPoolExecutor) req.getServletContext().getAttribute("executor");
        executor.submit(new AsyncRequestProcessor(asyncContext, secs));
        long endTime = System.currentTimeMillis();
        LOG.info("AsynchronousServlet: doGet. Thread: " + Thread.currentThread().getName() + ", id: " + Thread.currentThread().getId()
                + ", time taken: " + (endTime - startTime) + " ms.");
    }
}
