package com.esri.arcgis.militarymessaging;

import com.esri.arcgis.addins.desktop.DockableWindow;
import java.awt.Component;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javax.swing.JTextField;

/**
 * The class for a simple JavaFX Dockable Window.
 */
public class StreamLayerDockableWindow extends DockableWindow {

    public static final String ID = "com.esri.arcgis.militarymessaging.StreamLayerDockableWindow";

    private static final Logger LOGGER = Logger.getLogger(StreamLayerDockableWindow.class.getName());

    private JTextField textField_uri = null;
    private Button button_addStreamLayer = null;

    @Override
    public Component createUI() {
        final JFXPanel fxPanel = new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
        return fxPanel;
    }

    private void initFX(JFXPanel fxPanel) {
        /**
         * Workaround in case the JavaFX thread class loader is null because
         * this is ArcGIS rather than a standalone app.
         */
        if (null == Thread.currentThread().getContextClassLoader()) {
            Thread.currentThread().setContextClassLoader(StreamLayerDockableWindow.class.getClassLoader());
        }

        final Scene scene = createScene();

        // This method is invoked on JavaFX thread
        fxPanel.setScene(scene);
    }

    private Scene createScene() {
        VBox root = new VBox();
        Scene scene = new Scene(root, javafx.scene.paint.Color.ALICEBLUE);

        root.getChildren().add(new Text("Stream service URI"));
        textField_uri = new JTextField();
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(textField_uri);
        root.getChildren().add(swingNode);
        button_addStreamLayer = new Button("Add Stream Layer");
        button_addStreamLayer.setOnAction(this::button_addStreamLayer_onAction);
        root.getChildren().add(button_addStreamLayer);

        return (scene);
    }

    private void button_addStreamLayer_onAction(ActionEvent evt) {
        try {
            StreamServiceClient streamServiceClient = new StreamServiceClient(new URI(
                    textField_uri.getText()
            ));
            streamServiceClient.connect();
        } catch (URISyntaxException ex) {
            LOGGER.log(Level.WARNING, "Could not connect to stream service at " + textField_uri.getText(), ex);
        }
    }

}
