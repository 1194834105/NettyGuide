package main.http.httpfile.start;

import main.http.httpfile.server.HttpFileServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lc on 2017/10/31.
 */
public class MainStart {
    private static final String DEFAULT_URL = "/FileServer";
    private static Logger logger = LoggerFactory.getLogger(MainStart.class);

    public static void main(String[] args) {
        try {
            new HttpFileServer().run("127.0.0.1",9933, DEFAULT_URL);
        }catch (Exception e){
            logger.error("MainStart方法异常！", e);
        }
    }
}
