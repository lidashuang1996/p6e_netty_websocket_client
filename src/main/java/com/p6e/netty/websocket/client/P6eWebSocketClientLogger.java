package com.p6e.netty.websocket.client;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * 加载日志的工具类
 * @author LiDaShuang
 * @version 1.0
 */
public class P6eWebSocketClientLogger {

    /** 日志注入对象 */
    private static final Logger logger = LoggerFactory.getLogger("LOGGER_CONFIG");

    /** 是否加载过 logback 配置信息 */
    private static volatile boolean bool = false;

    /** 加载 logback 配置信息 */
    public synchronized static void init() {
        init("./logback.xml");
    }

    /** 加载 logback 配置信息 */
    public synchronized static void init(String name) {
        if (!bool) {
            try {
                URL filePath = P6eWebSocketClientLogger.class.getClassLoader().getResource(name);
                if (filePath == null) throw new NullPointerException(P6eWebSocketClientLogger.class.toString() + " filePath");
                LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
                JoranConfigurator configurator = new JoranConfigurator();
                configurator.setContext(loggerContext);
                loggerContext.reset();
                configurator.doConfigure(filePath);
                StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
                bool = true;
                headerLog();
            } catch (JoranException e) {
                bool = false;
                e.printStackTrace();
            }
        }
    }

    /** 头部输出的日志 */
    private static void headerLog() {
        // http://patorjk.com/software/taag/#p=display&h=1&f=Ogre&t=P6e%20netty%20web%20socket%20client
        // ogre
        logger.info("");
        logger.info("   ___   __                        _    _                           _                         _          _           _  _               _   ");
        logger.info("  / _ \\ / /_    ___   _ __    ___ | |_ | |_  _   _  __      __ ___ | |__    ___   ___    ___ | | __ ___ | |_    ___ | |(_)  ___  _ __  | |_ ");
        logger.info(" / /_)/| '_ \\  / _ \\ | '_ \\  / _ \\| __|| __|| | | | \\ \\ /\\ / // _ \\| '_ \\  / __| / _ \\  / __|| |/ // _ \\| __|  / __|| || | / _ \\| '_ \\ | __|");
        logger.info("/ ___/ | (_) ||  __/ | | | ||  __/| |_ | |_ | |_| |  \\ V  V /|  __/| |_) | \\__ \\| (_) || (__ |   <|  __/| |_  | (__ | || ||  __/| | | || |_ ");
        logger.info("\\/      \\___/  \\___| |_| |_| \\___| \\__| \\__| \\__, |   \\_/\\_/  \\___||_.__/  |___/ \\___/  \\___||_|\\_\\\\___| \\__|  \\___||_||_| \\___||_| |_| \\__|");
        logger.info("                                             |___/                                                                                          ");
        logger.info("");
    }

}
