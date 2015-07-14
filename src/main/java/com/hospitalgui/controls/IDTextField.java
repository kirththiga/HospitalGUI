package com.hospitalgui.controls;

import javafx.scene.control.TextField;

/**
 * This class extends the TextField so that whenever new text is entered or a
 * portion of the text is replaced then the string is compared to a regular
 * expression. In this case the regular expression accepts any number. 
 *
 * Thank you Ken Fogel for providing this code!
 * 
 *  
 *
 */
public class IDTextField extends TextField {
    
    // Regex for any number or whiteshape
    String numberRegEx = "^[0-9 ]*$";
    
/*    @Override
    public void replaceText(int start, int end, String text) {
        String oldText = getText();
        if(text.matches(numberRegEx)) {
            super.replaceText(start, end, text);
            String newText = super.getText();
            if(!newText.matches(numberRegEx)) {
                super.setText((oldText));
            }
        }
    } */
    

    @Override
    public void replaceText(int start, int end, String text) {
        String oldValue = getText();
        if (text.matches(numberRegEx)) {
            super.replaceText(start, end, text);
            String newText = super.getText();
            if (!newText.matches(numberRegEx)) {
                super.setText(oldValue);
            }
        }
    }
    
    
    @Override
    public void replaceSelection(String text) {
        String oldValue = getText();
        if (!text.matches(numberRegEx)) {
            super.replaceSelection(text);
            String newText = super.getText();
            if (!newText.matches(numberRegEx)) {
                super.setText(oldValue);
            }
        }
    }
    
}
