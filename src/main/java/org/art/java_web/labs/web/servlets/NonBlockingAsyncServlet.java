package org.art.java_web.labs.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Simple non-blocking asynchronous servlet code example.
 */
@WebServlet(urlPatterns = {"/nonblock.call"}, asyncSupported = true)
public class NonBlockingAsyncServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(AsynchronousServlet.class);

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("NonBlockingAsyncServlet: doPost. Thread: " + Thread.currentThread().getName());
        AsyncContext asyncContext = req.startAsync();
        ServletInputStream in = req.getInputStream();

        in.setReadListener(new ReadListener() {
            byte[] buffer = new byte[4 * 1024];

            @Override
            public void onDataAvailable() {
                try {
                    String str;
                    do {
                        int length = in.read(buffer);
                        if (length > 0) {
                            str = new String(buffer, 0, length);
                            LOG.info("ReadListener: onDataAvailable: I am reading bytes from remote server. Thread: "
                                    + Thread.currentThread().getName());
                            sb.append(str);
                        }
                    } while (in.isReady());
                } catch (IOException e) {
                    LOG.error("IOException has been caught!", e);
                }
            }

            @Override
            public void onAllDataRead() {
                try {
                    LOG.info("Request data (fully read): " + sb.toString());
                    asyncContext.getResponse().getWriter().write("The response has been read!");
                } catch (IOException e) {
                    LOG.error("IOException has been caught!", e);
                }
            }

            @Override
            public void onError(Throwable t) {
                LOG.error("ReadListener: onError", t);
            }
        });

        //Thread gets this code line without blocking on a reading from remote server!!!
        LOG.info("ReadListener: onDataAvailable: I am not blocked =) and doing some other job here... Thread: " + Thread.currentThread().getName());
    }
}
