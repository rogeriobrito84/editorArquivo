package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class Teste extends Application{
	private HTMLEditor htmlEditor = null;
	TextArea htmlLabel;
             

     

    private void init(Stage primaryStage) {

        Group root = new Group();

        primaryStage.setScene(new Scene(root));

        VBox vRoot = new VBox();

 

        vRoot.setPadding(new Insets(8, 8, 8, 8));

        vRoot.setSpacing(5);

 

        htmlEditor = new HTMLEditor();

        htmlEditor.setPrefSize(500, 245);


        vRoot.getChildren().add(htmlEditor);

 

        htmlLabel = new TextArea();

        htmlLabel.setMaxWidth(500);

        htmlLabel.setWrapText(true);

 

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.getStyleClass().add("noborder-scroll-pane");

        scrollPane.setContent(htmlLabel);

        scrollPane.setFitToWidth(true);

        scrollPane.setPrefHeight(180);

 

        Button showHTMLButton = new Button("Show the HTML below");

        vRoot.setAlignment(Pos.CENTER);

        showHTMLButton.setOnAction(new EventHandler<ActionEvent>() {

 

            @Override

            public void handle(ActionEvent arg0) {
            	String semTags = retirarTags(htmlEditor.getHtmlText());
                htmlLabel.setText(semTags);

            }

        });

 

        vRoot.getChildren().addAll(showHTMLButton, scrollPane);

        root.getChildren().addAll(vRoot);

    }

 

    @Override public void start(Stage primaryStage) throws Exception {

        init(primaryStage);

        primaryStage.show();

    }
    
    public String retirarTags(String texto){
    	texto = texto.replaceAll("</p>", "\n");
    	texto = texto.replaceAll("<[^>]*>", "");
    	texto = texto.replaceAll("&lt;", "<");
    	texto = texto.replaceAll("&gt;", ">");
    	texto = texto.replaceAll("&nbsp;", " ");
    	texto = texto.replaceAll("&amp;", "&");
    	if(texto.endsWith("\n")){
    		texto = texto.substring(0,texto.length()-1);
    	}
    	return texto;
    }
    
    public static void main(String[] args) { launch(args); }
		
		
	}

