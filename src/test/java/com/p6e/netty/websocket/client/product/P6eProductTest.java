package com.p6e.netty.websocket.client.product;

import com.p6e.netty.websocket.client.instructions.P6eInstructionsDefault;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class P6eProductTest {

    @Test
    public void structureTest() throws URISyntaxException {
        Map<String, Object> headers = new HashMap<>();
        headers.put("H_NAME", "H_VALUE");

        System.out.println(new P6eProduct());
        System.out.println(new P6eProduct("wss://127.0.0.1/path_content?id=123&name=中文"));
        System.out.println(new P6eProduct(new URI("wss://127.0.0.1/path_content?id=123&name=中文")));
        System.out.println(new P6eProduct("ID CONTENT 1", "wss://127.0.0.1/path_content?id=123&name=中文"));
        System.out.println(new P6eProduct("ID CONTENT 2", new URI("wss://127.0.0.1/path_content?id=123&name=中文")));

        System.out.println(new P6eProduct("wss://127.0.0.1/path_content?id=123&name=中文", headers));
        System.out.println(new P6eProduct("ID CONTENT 3","wss://127.0.0.1/path_content?id=123&name=中文", headers));

        System.out.println(new P6eProduct(new URI("wss://127.0.0.1/path_content?id=123&name=中文"), headers));
        System.out.println(new P6eProduct("ID CONTENT 4", new URI("wss://127.0.0.1/path_content?id=123&name=中文"), headers));

        System.out.println(new P6eProduct("wss://127.0.0.1/path_content?id=123&name=中文", new P6eInstructionsDefault()));
        System.out.println(new P6eProduct("ID CONTENT 5", "wss://127.0.0.1/path_content?id=123&name=中文", new P6eInstructionsDefault()));

        System.out.println(new P6eProduct(new URI("wss://127.0.0.1/path_content?id=123&name=中文"), new P6eInstructionsDefault()));
        System.out.println(new P6eProduct("ID CONTENT 6", new URI("wss://127.0.0.1/path_content?id=123&name=中文"), new P6eInstructionsDefault()));


        System.out.println(new P6eProduct("wss://127.0.0.1/path_content?id=123&name=中文", headers, new P6eInstructionsDefault()));
        System.out.println(new P6eProduct("ID CONTENT 7", "wss://127.0.0.1/path_content?id=123&name=中文", headers, new P6eInstructionsDefault()));

        System.out.println(new P6eProduct(new URI("wss://127.0.0.1/path_content?id=123&name=中文"), headers, new P6eInstructionsDefault()));
        System.out.println(new P6eProduct("ID CONTENT 8", new URI("wss://127.0.0.1/path_content?id=123&name=中文"), headers, new P6eInstructionsDefault()));

        P6eProduct p6eProduct = new P6eProduct("wss://127.0.0.1/path_content?id=123&name=中文", headers, new P6eInstructionsDefault());

        System.out.println("p6eProduct.getPort() " + p6eProduct.getPort());
        System.out.println("p6eProduct.getId() " + p6eProduct.getId());
        System.out.println("p6eProduct.getHost() " + p6eProduct.getHost());
        System.out.println("p6eProduct.getAgreement() " + p6eProduct.getAgreement());
        System.out.println("p6eProduct.getPath() " + p6eProduct.getPath());
        System.out.println("p6eProduct.getParam() " + p6eProduct.getParam());
        System.out.println("p6eProduct.getUri() " + p6eProduct.getUri());
        System.out.println("p6eProduct.getInstructions() " + p6eProduct.getInstructions());
        System.out.println("p6eProduct.getHttpHeaders() " + p6eProduct.getHttpHeaders());

    }

}
