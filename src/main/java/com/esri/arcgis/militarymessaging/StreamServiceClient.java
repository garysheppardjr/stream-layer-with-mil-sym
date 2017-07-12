package com.esri.arcgis.militarymessaging;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Connects to a stream service. Instantiate first and then call connect() to
 * start getting features.
 */
public class StreamServiceClient extends WebSocketClient {

    private static final Logger LOGGER = Logger.getLogger(StreamServiceClient.class.getName());

    public StreamServiceClient(URI wsUri) {
        super(wsUri);
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
            setSocket(sslContext.getSocketFactory().createSocket());
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException ex) {
            Logger.getLogger(StreamServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override
    public final void setSocket(Socket socket) {
        super.setSocket(socket);
    }

    @Override
    public void onOpen(ServerHandshake sh) {
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (remote) {
            connect();
        }
    }

    @Override
    public void onError(Exception ex) {
        LOGGER.log(Level.SEVERE, null, ex);
    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        StreamServiceClient streamServiceClient = new StreamServiceClient(new URI(
                "wss://idt-portal.dc.esri.com:6143/arcgis/ws/services/MilitaryMessageStream/StreamServer/subscribe"
        ));
        streamServiceClient.connect();
    }

}
