package com.patryk.mathdoku;


import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class ManualGameInputDialog extends Dialog<String> {
    TextArea textArea;
    public ManualGameInputDialog() {
        getDialogPane().setContent( textArea = new TextArea());
        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        setResultConverter(buttonType -> textArea.getText());
    }
}
