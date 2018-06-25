package org.art.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;

public class AsyncRequestProcessor implements Runnable {

    private static final Logger LOG = LogManager.getLogger(AsyncRequestProcessor.class);

    private AsyncContext asyncContext;

    private int seconds;

    public AsyncRequestProcessor() {
    }

    public AsyncRequestProcessor(AsyncContext asyncContext, int seconds) {
        this.asyncContext = asyncContext;
        this.seconds = seconds;
    }

    @Override
    public void run() {
        LOG.info("AsyncRequestProcessor: async supported: " + asyncContext.getRequest().isAsyncSupported());
        try {
            longProcessing(seconds);
            PrintWriter out = asyncContext.getResponse().getWriter();
            out.write("Processing done for " + seconds + " seconds!");
        } catch (IOException e) {
            LOG.error("IOException has been caught!", e);
        }
        //Complete the processing
        asyncContext.complete();
    }

    private void longProcessing(int seconds) {
        //Wait for given time before finishing
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException has been caught!", e);
        }
    }
}
