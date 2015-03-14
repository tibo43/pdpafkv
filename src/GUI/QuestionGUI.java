/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BDD.DataBase;
import BDD.Language;
import BDD.Question;
import Controller.SelectMedia;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Jeremy
 */
public class QuestionGUI extends Parent {

    private final SelectMedia controlSM;
    private Text texte_entier = new Text();
    
    public QuestionGUI(Language langSel, DataBase db){
        
        this.controlSM = new SelectMedia(db,langSel);
        this.launchQuest(db,langSel);
    }
    
    private void launchQuest(DataBase db, Language langue){
        
        
        FlowPane fond_question = new FlowPane();
        fond_question.setVgap(8);
        fond_question.setHgap(4);
        fond_question.setPrefWrapLength(300);
        fond_question.setPadding(new Insets(15, 12, 15, 12));
        fond_question.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2; -fx-border-color: #000000;");
        fond_question.setAlignment(Pos.CENTER);

        
        //Question
        //Question selQuestion = this.controlSM.SelectQuestion();
        texte_entier.setText(controlSM.SelectQuestion().getContent());
        texte_entier.setFont(new Font(30));
        texte_entier.setFill(Color.GREY);
        
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-45.0);
        Lighting li = new Lighting();
        li.setLight(light);
        texte_entier.setEffect(li);
        
        
        fond_question.getChildren().add(texte_entier);//ajout du texte a la zone
        this.getChildren().add(fond_question);
    }
    
    public  Text getText(){
        return texte_entier;
    }
}
