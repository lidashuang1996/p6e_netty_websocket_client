package com.p6e.netty.websocket.client;

import com.p6e.netty.websocket.client.actuator.P6eActuatorAbstractAsync;
import com.p6e.netty.websocket.client.model.P6eModel;
import com.p6e.netty.websocket.client.model.P6eModelCache;
import com.p6e.netty.websocket.client.model.P6eNioModel;
import com.p6e.netty.websocket.client.config.P6eConfig;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Netty Web Socket 应用类
 * @author LiDaShuang
 * @version 1.0.0
 */
public class P6eWebsocketClientApplication {
    private P6eModel p6eModel;
    private ThreadPoolExecutor threadPool;

    public static P6eWebsocketClientApplication run() {
        return new P6eWebsocketClientApplication(P6eNioModel.class, new P6eConfig[] {});
    }

    public static P6eWebsocketClientApplication run(P6eModel model) {
        return new P6eWebsocketClientApplication(model, new P6eConfig[] {});
    }

    public static P6eWebsocketClientApplication run(Class<? extends P6eModel> model) {
        return new P6eWebsocketClientApplication(model, new P6eConfig[] {});
    }

    public static P6eWebsocketClientApplication run(P6eModel model, P6eConfig... products) {
        return new P6eWebsocketClientApplication(model, products);
    }

    public static P6eWebsocketClientApplication run(Class<? extends P6eModel> model, P6eConfig... products) {
        return new P6eWebsocketClientApplication(model, products);
    }

    public static P6eWebsocketClientApplication run(P6eModel model, ThreadPoolExecutor threadPool) {
        return new P6eWebsocketClientApplication(model, threadPool, new P6eConfig[]{});
    }

    public static P6eWebsocketClientApplication run(Class<? extends P6eModel> model, ThreadPoolExecutor threadPool) {
        return new P6eWebsocketClientApplication(model, threadPool, new P6eConfig[]{});
    }

    public static P6eWebsocketClientApplication run(P6eModel model, ThreadPoolExecutor threadPool, P6eConfig... products) {
        return new P6eWebsocketClientApplication(model, threadPool, products);
    }

    public static P6eWebsocketClientApplication run(Class<? extends P6eModel> model, ThreadPoolExecutor threadPool, P6eConfig... products) {
        return new P6eWebsocketClientApplication(model, threadPool, products);
    }

    public P6eWebsocketClientApplication(Class<? extends P6eModel> model, P6eConfig[] products) {
        try {
            this.threadPool = new ThreadPoolExecutor(0, 30, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
            this.p6eModel = model.newInstance().run().connect(products);
            P6eActuatorAbstractAsync.init(threadPool);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public P6eWebsocketClientApplication(P6eModel model, P6eConfig[] products) {
        this.threadPool = new ThreadPoolExecutor(0, 30, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        this.p6eModel = model.connect(products);
        P6eActuatorAbstractAsync.init(threadPool);
    }

    public P6eWebsocketClientApplication(Class<? extends P6eModel> model, ThreadPoolExecutor threadPool, P6eConfig[] products) {
        try {
            this.threadPool = threadPool;
            this.p6eModel = model.newInstance().run().connect(products);
            P6eActuatorAbstractAsync.init(threadPool);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public P6eWebsocketClientApplication(P6eModel model, ThreadPoolExecutor threadPool, P6eConfig[] products) {
        this.threadPool = threadPool;
        this.p6eModel = model.connect(products);
        P6eActuatorAbstractAsync.init(threadPool);
    }

    public P6eWebsocketClientApplication connect(P6eConfig product) {
        this.p6eModel.connect(product);
        return this;
    }

    public P6eWebsocketClientApplication connect(P6eConfig[] products) {
        this.p6eModel.connect(products);
        return this;
    }

    public P6eWebsocketClientApplication close() {
        this.p6eModel.close();
        return this;
    }

    public P6eWebsocketClientApplication close(String id) {
        this.p6eModel.close(id);
        return this;
    }

    public P6eWebsocketClientApplication close(String[] ids) {
        this.p6eModel.close(ids);
        return this;
    }

    public void destroy() {
        this.p6eModel.close();
        if (threadPool != null && !threadPool.isShutdown()) threadPool.shutdown();
    }

    public P6eModelCache getP6eModelCache() {
        return this.p6eModel.getP6eModelCache();
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }
}
