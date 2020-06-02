package com.p6e.netty.websocket.client.config;

import com.p6e.netty.websocket.client.actuator.P6eActuatorDefault;
import com.p6e.netty.websocket.client.actuator.P6eBaseActuator;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;

public class P6eConfigTest {

    @Test
    public void test() throws URISyntaxException {
        /* 无参数的构造方法构建 */
        P6eConfig p6eConfig1 = new P6eConfig();
        Assert.assertEquals(p6eConfig1.toString(),
                "{\"port\":10000,\"host\":\"127.0.0.1\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://127.0.0.1:10000,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 协议 ws 不带端口 */
        P6eConfig p6eConfig2 = new P6eConfig("ws://0.0.0.0");
        Assert.assertEquals(p6eConfig2.toString(),
                "{\"port\":80,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:80,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 协议 ws 带端口 */
        P6eConfig p6eConfig3 = new P6eConfig("ws://0.0.0.0:5000");
        Assert.assertEquals(p6eConfig3.toString(),
                "{\"port\":5000,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:5000,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 协议 wss 不带端口 */
        P6eConfig p6eConfig4 = new P6eConfig("wss://0.0.0.0");
        Assert.assertEquals(p6eConfig4.toString(),
                "{\"port\":443,\"host\":\"0.0.0.0\",\"agreement\":\"wss\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":wss://0.0.0.0:443,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 协议 wss 带端口 */
        P6eConfig p6eConfig5 = new P6eConfig("wss://0.0.0.0:6000");
        Assert.assertEquals(p6eConfig5.toString(),
                "{\"port\":6000,\"host\":\"0.0.0.0\",\"agreement\":\"wss\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":wss://0.0.0.0:6000,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 大小写测试 */
        P6eConfig p6eConfig6 = new P6eConfig("WS://0.0.0.0:5000");
        Assert.assertEquals(p6eConfig6.toString(),
                "{\"port\":5000,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:5000,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 大小写测试 */
        P6eConfig p6eConfig7 = new P6eConfig("WsS://0.0.0.0:6000");
        Assert.assertEquals(p6eConfig7.toString(),
                "{\"port\":6000,\"host\":\"0.0.0.0\",\"agreement\":\"wss\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":wss://0.0.0.0:6000,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 路径测试 */
        P6eConfig p6eConfig8 = new P6eConfig("ws://0.0.0.0:5000/path_content");
        Assert.assertEquals(p6eConfig8.toString(),
                "{\"port\":5000,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"/path_content\",\"param\":\"\",\"uri\":ws://0.0.0.0:5000/path_content,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 参数测试 */
        P6eConfig p6eConfig9 = new P6eConfig("ws://0.0.0.0:5000/path_content?param=param_content");
        Assert.assertEquals(p6eConfig9.toString(),
                "{\"port\":5000,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"/path_content\",\"param\":\"param=param_content\",\"uri\":ws://0.0.0.0:5000/path_content?param=param_content,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 头部测试 */
        Map<String, Object> headers = new Hashtable<>();
        headers.put("header", "header_content");
        P6eConfig p6eConfig10 = new P6eConfig("ws://0.0.0.0", headers);
        Assert.assertEquals(p6eConfig10.toString(),
                "{\"port\":80,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:80,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{header=header_content},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 COOKIES 测试 */
        P6eConfig.Cookie p6eCookie = new P6eConfig.Cookie("cookie_name", "cookie_value");
        P6eConfig p6eConfig11 = new P6eConfig("ws://0.0.0.0", p6eCookie);
        Assert.assertEquals(p6eConfig11.toString(),
                "{\"port\":80,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:80,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[" + p6eCookie + "]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 回调函数测试 */
        P6eBaseActuator p6eBaseActuator = new P6eActuatorDefault();
        String actuator = p6eBaseActuator.toString();
        P6eConfig p6eConfig12 = new P6eConfig("ws://0.0.0.0", p6eBaseActuator);
        Assert.assertEquals(p6eConfig12.toString(),
                "{\"port\":80,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:80,\"actuator\":" + actuator + "," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");


        /* 字符串 URI 的构造方法构建 修改内容测试 */
        P6eBaseActuator actuator13 = new P6eActuatorDefault();
        P6eConfig p6eConfig13 = new P6eConfig("ws://0.0.0.0");
        p6eConfig13.setNettyLogLevel(P6eConfig.LogLevel.DEBUG); // netty 日志级别 NONE
        p6eConfig13.setNettyLoggerBool(true); // netty 是否开启 false
        p6eConfig13.setActuator(actuator13); // 回调的执行器 同步回调
        p6eConfig13.setVersion(P6eConfig.Version.V13); // WEBSOCKET 的版本号 13
        p6eConfig13.setSslPath(null); // 自定义的 SSL 证书目录 不使用
        Assert.assertEquals(p6eConfig13.toString(),
                "{\"port\":80,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:80,\"actuator\":" + actuator13 + "," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":true,\"nettyLogLevel\":\"DEBUG\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");


        /* 字符串 URI 的构造方法构建 修改内容 URI 测试 */
        P6eConfig p6eConfig14 = new P6eConfig("ws://0.0.0.0");
        p6eConfig14.setUri(new URI("WSs://1.1.1.1"));
        Assert.assertEquals(p6eConfig14.toString(),
                "{\"port\":443,\"host\":\"1.1.1.1\",\"agreement\":\"wss\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":wss://1.1.1.1:443,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");

        /* 字符串 URI 的构造方法构建 添加请求头和 COOKIES 测试 */
        P6eConfig p6eConfig15 = new P6eConfig("ws://0.0.0.0");
        P6eConfig.Cookie p6eCookie15 = new P6eConfig.Cookie("cookie_name", "cookie_value");
        p6eConfig15.addCookies(p6eCookie15);
        Map<String, Object> headers15 = new Hashtable<>();
        headers15.put("header", "header_content");
        p6eConfig15.addHttpHeaders(headers15);
        Assert.assertEquals(p6eConfig15.toString(),
                "{\"port\":80,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:80,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[" + p6eCookie15 + "]," +
                        "\"httpHeaders\":{header=header_content},\"sslPath\":\"null\"}");
        /* 字符串 URI 的构造方法构建 清除请求头和 COOKIES 测试 */
        p6eConfig15.clearCookies();
        p6eConfig15.clearHttpHeaders();
        Assert.assertEquals(p6eConfig15.toString(),
                "{\"port\":80,\"host\":\"0.0.0.0\",\"agreement\":\"ws\"," +
                        "\"path\":\"\",\"param\":\"\",\"uri\":ws://0.0.0.0:80,\"actuator\":null," +
                        "\"version\":\"V13\",\"nettyLoggerBool\":false,\"nettyLogLevel\":\"NONE\",\"cookies\":[]," +
                        "\"httpHeaders\":{},\"sslPath\":\"null\"}");
    }
}
