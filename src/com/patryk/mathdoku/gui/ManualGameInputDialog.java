package com.patryk.mathdoku.gui;


import javafx.event.EventTarget;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class ManualGameInputDialog extends Dialog<String> {
    private TextArea textArea;
    public ManualGameInputDialog() {
        getDialogPane().setContent( textArea = new TextArea());
        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        setResizable(true);

        setResultConverter(buttonType -> {
            if (buttonType.equals(ButtonType.OK))
                return textArea.getText();
            else {
                return null;
            }
        });

    }
}
