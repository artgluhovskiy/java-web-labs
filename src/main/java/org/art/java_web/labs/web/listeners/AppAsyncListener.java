package org.art.java_web.labs.web.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AppAsyncListener implements AsyncListener {

    private static final Logger LOG = LogManager.getLogger(AppAsyncListener.class);

    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        LOG.info("AppAsyncListener: onComplete");
        //We can do resource clean up activity here
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        LOG.info("AppAsyncListener: onTimeout");
        ServletResponse resp = event.getAsyncContext().getResponse();
        PrintWriter out = resp.getWriter();
        out.write("Timeout Error in Processing!");
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        LOG.info("AppAsyncListener: onError");
        //We can return error response to client
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        LOG.info("AppAsyncListener: onStartAsync");
        //We can log the event here
    }
}
