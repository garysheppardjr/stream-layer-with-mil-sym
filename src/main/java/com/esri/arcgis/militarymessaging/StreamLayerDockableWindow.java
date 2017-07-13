package com.esri.arcgis.militarymessaging;

import com.esri.arcgis.addins.desktop.DockableWindow;
import java.awt.Component;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

/**
 * The class for a simple JavaFX Dockable Window.
 */
public class StreamLayerDockableWindow extends DockableWindow {

    public static final String ID = "com.esri.arcgis.militarymessaging.StreamLayerDockableWindow";

    private TextField textField_uri = null;

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
        textField_uri = new TextField();
        textField_uri.setPromptText("e.g. wss://host.domain.com:6143/arcgis/ws/services/MyStreamService/StreamServer");
        root.getChildren().add(textField_uri);

        return (scene);
    }

}
