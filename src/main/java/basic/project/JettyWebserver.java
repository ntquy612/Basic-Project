package basic.project;

import basic.project.config.Config;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;


@Component
public class JettyWebserver implements WebServerFactoryCustomizer<JettyServletWebServerFactory> {
    @Autowired
    Config config;
    @Override
    public void customize(JettyServletWebServerFactory factory) {
        int minThread = config.get("jetty.minThread",Runtime.getRuntime().availableProcessors(),Integer.class);
        int maxThread = config.get("jetty.maxThread",minThread*2,Integer.class);
        int idleTimeout = config.get("jetty.idleTimeout",5000,Integer.class);
        int maxQueueSize = config.get("jetty.maxQueueSize",maxThread*10000,Integer.class);
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(maxQueueSize);
        ThreadPool threadPool = new QueuedThreadPool(
                maxThread,
                minThread,
                idleTimeout,
                blockingQueue
        );
        factory.setThreadPool(threadPool);
        String serverInfoTemplate = """
                =================== Jetty Server ==========
                === port: %10d                    ===
                === min thread: %10d              ===
                === max thread: %10d              ===
                === idle timeout: %10d            ===
                === max queue size: %10d          ===
                ===========================================
                """;
        String serverInfo =String.format(serverInfoTemplate, factory.getPort(), minThread, maxThread, idleTimeout, maxQueueSize);
        LoggerFactory.getLogger("console").info(serverInfo);
    }
}
