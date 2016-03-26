package nsccsclub.isogum;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * The input keyboard for the ISO GUM uncertainty calculator
 * Created by csconway on 3/25/2016.
 * Adapted from an articles by Maarten Pennings
 * website for Maarten: www.fampennings.nl/maarten/android/09keyboard.html
 * Used code from stack exchange
 * stackoverflow.com/questions/16174179/set-keyboard-mode-in-android-custom-keyboard
 */
public class InputKeyboard implements KeyboardView.OnKeyboardActionListener{
    //TODO allow for multiple screens

    /**
     * The view containing the keyboard
     */
    private KeyboardView keyboardView;

    /**
     * The activity hosting the keyboard
     */
    private Activity host;

    /**
     * The listener for all key events in the keyboard
     */
    private OnKeyboardActionListener onKeyboardActionListener;

    //SUPPORTED KEYS AND KEYCODES
    //TODO WRITE A PROCEDURE FOR ADDING A KEY
    //TODO CREATE KEY CODE CHART

    //TODO add implementation for mulitiple kinds of keyboards

    /**
     * Constructor for the custom ISO GUM math keyboard.
     * @param host Hosting activity
     * @param viewid The view registered with the keyboard.
     * @param layoutid The layout of the keyboard.
     */
    public InputKeyboard(Activity host, int viewid, int layoutid){
        //initializes intstance varaibles of the class
        this.host= host;
        keyboardView=(KeyboardView)host.findViewById(viewid);
        //hooks up the keyboard
        keyboardView.setKeyboard(new Keyboard(host, layoutid));
        keyboardView.setOnKeyboardActionListener(this);
        //keyboard settings
        keyboardView.setPreviewEnabled(false);
    }


    /**
     * Checks if the keyboard is currently visible.
     * @return True if visible, false if not.
     */
    public boolean isKeyboardVisible(){
        return keyboardView.getVisibility()== View.VISIBLE;
    }

    /**
     * Shows the custom keyboard on the view screen and disables default
     * @param v The view we are currently working in.
     */
    public void showInputKeyboard(View v){
        keyboardView.setVisibility(View.VISIBLE);
        keyboardView.setEnabled(true);
        if(v!=null){
            ((InputMethodManager)host.getSystemService(Activity.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    /**
     * Hides the keyboard from view
     */
    public void hideInputKeyboard() {
        keyboardView.setVisibility(View.GONE);
        keyboardView.setEnabled(false);
    }

    /**
     * Registers an edit text field with this keyboard.
     * @param resID The EditText to register with this keyboard.
     */
    public void registerEditText(int resID){
        //object we are working with
        final EditText editText = (EditText)host.findViewById(resID);
        //makes keyboard appear on user focus and dissapear when focus lost
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showInputKeyboard(v);
                }
                else {
                    hideInputKeyboard();
                }
            }
        });
        //set visible on user click
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputKeyboard(v);
            }
        });
        //HARD DISABLES USER KEYBOARD FOR ENTIRE ACTIVITY, NO SWITCHING BACK AND FORTH!
        //much credit to Maarten Pennings see class description for details
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //backup input and flash on and off
                //should get keyboard working
                int inType = editText.getInputType();
                editText.setInputType(InputType.TYPE_NULL);
                editText.onTouchEvent(event);
                editText.setInputType(inType);
                //consumes the touch event
                return true;
            }
        });
        //GENERAL EDIT TEXT SETTINGS
        //disable spellcheck we have strange input
        editText.setInputType(editText.getInputType()| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    //IMPLEMENTATION OF THE LISTENER

    /**
     * Called when the user presses a key. This is sent before the {@link #onKey} is called.
     * For keys that repeat, this is only called once.
     *
     * @param primaryCode the unicode of the key being pressed. If the touch is not on a valid
     *                    key, the value will be zero.
     */
    @Override
    public void onPress(int primaryCode) {

    }

    /**
     * Called when the user releases a key. This is sent after the {@link #onKey} is called.
     * For keys that repeat, this is only called once.
     *
     * @param primaryCode the code of the key that was released
     */
    @Override
    public void onRelease(int primaryCode) {

    }

    /**
     * Send a key press to the listener.
     *
     * @param primaryCode this is the key that was pressed
     * @param keyCodes    the codes for all the possible alternative keys
     *                    with the primary code being the first. If the primary key code is
     *                    a single character such as an alphabet or number or symbol, the alternatives
     *                    will include other characters that may be on the same key or adjacent keys.
     *                    These codes are useful to correct for accidental presses of a key adjacent to
     */
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        //adapted from Maarten Pennings under Apache License
        //see url in class description

        //get our edit text window that we are working with checks for incompatable type
        View focus = host.getWindow().getCurrentFocus();
        //todo check this very throroughly in debugger, this should be working
//        if(focus==null||focus.getClass()!=EditText.class){
//            return;
//        }

        EditText editText = (EditText)focus;
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        //performs the action associated with the code

        switch (primaryCode){
            default:                                                //basic unicode, insert char
                editable.insert(start,Character.toString((char)primaryCode));
                break;
        }
    }

    /**
     * Sends a sequence of characters to the listener.
     *
     * @param text the sequence of characters to be displayed.
     */
    @Override
    public void onText(CharSequence text) {

    }

    /**
     * Called when the user quickly moves the finger from right to left.
     */
    @Override
    public void swipeLeft() {

    }

    /**
     * Called when the user quickly moves the finger from left to right.
     */
    @Override
    public void swipeRight() {

    }

    /**
     * Called when the user quickly moves the finger from up to down.
     */
    @Override
    public void swipeDown() {

    }

    /**
     * Called when the user quickly moves the finger from down to up.
     */
    @Override
    public void swipeUp() {

    }

}
