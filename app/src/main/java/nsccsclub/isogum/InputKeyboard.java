package nsccsclub.isogum;
//todo create parser for easy debugging

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static nsccsclub.isogum.R.integer.keyboard_number;

/**
 * The input keyboard for the ISO GUM uncertainty calculator
 * Created by csconway on 3/25/2016.
 * Adapted from an articles by Maarten Pennings
 * website for Maarten: www.fampennings.nl/maarten/android/09keyboard.html
 * Used code from stack exchange
 * stackoverflow.com/questions/16174179/set-keyboard-mode-in-android-custom-keyboard
 */
public class InputKeyboard{

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

    /**
     * The current screen of the keyboard
     */
    private int keyboardState;

    /**
     * The ABC keyboard configuration.
     */
    private Keyboard key_abc;


    /**
     * The shifted ABC keyboard configuration.
     */
    private Keyboard key_abc_shift;

    /**
     * The 123 keyboard congfiguration.
     */
    private Keyboard key_123;

    /**
     * The math keyboard configuration.
     */
    private Keyboard key_math;

    //SUPPORTED KEYS AND KEYCODES
    //TODO WRITE A PROCEDURE FOR ADDING A KEy
    public static final int CODE_MATH_KEY =             500000;
    public static final int CODE_123_KEY  =             500001;
    public static final int CODE_ABC_KEY  =             500002;
    public static final int CODE_ABC_KEY_SHIFT  =       500003;
    public static final int CODE_ALL_LEFT =             500004;
    public static final int CODE_LEFT =                 500005;
    public static final int CODE_RIGHT =                500006;
    public static final int CODE_ALL_RIGHT =            500007;
    public static final int CODE_EXP =                  500008;
    public static final int CODE_PI =                   500009;
    public static final int CODE_SIN =                  500010;
    public static final int CODE_ASIN =                 500011;
    public static final int CODE_LN =                   500012;
    public static final int CODE_SQRT =                 500013;
    public static final int CODE_COS =                  500014;
    public static final int CODE_ACOS =                 500015;
    public static final int CODE_LOG10 =                500016;
    public static final int CODE_FACTORIAL =            500017;
    public static final int CODE_TAN =                  500018;
    public static final int CODE_ATAN =                 500019;
    public static final int CODE_LOGN =                 500020;
    public static final int CODE_ABS =                  500021;
//    NOTE android provides the code of -5 for delete or KEYCODE_DELETE;



    /**
     * Constructor for the custom ISO GUM math keyboard.
     * @param host Hosting activity
     * @param viewid The view registered with the keyboard.
     * @param layoutid The layout of the keyboard.
     */
    public InputKeyboard(final Activity host, int viewid, int layoutid){
        //initializes intstance varaibles of the class
        this.host= host;                                            //host activity
        keyboardView=(KeyboardView)host.findViewById(viewid);       //host view
        keyboardState= CODE_ABC_KEY;                                 //default keyboard screen
        //creates all keyboard screens for the app seperate one needed for each implementation
        key_abc = new Keyboard(host, layoutid, R.integer.keyboard_text);
        key_abc_shift = new Keyboard(host, layoutid, R.integer.keyboard_text_shift);
        key_123 = new Keyboard(host, layoutid, R.integer.keyboard_number);
        key_math = new Keyboard(host, layoutid, R.integer.keyboard_math);
        //hooks up the keyboard
        keyboardView.setKeyboard(key_abc);
//        keyboardView.setOnKeyboardActionListener(this);
        keyboardView.setOnKeyboardActionListener(
                new OnKeyboardActionListener() {
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
                        //todo refactor this method for better design
                        //adapted from Maarten Pennings under Apache License
                        //see url in class description

                        //get our edit text window that we are working with checks for incompatable type
                        View focus = host.getWindow().getCurrentFocus();
                        //todo check this very throroughly in debugger, this should be working
//                        if(focus==null||focus.getClass()!=EditText.class){
//                            return;
//                        }

                        EditText editText = (EditText) focus;
                        Editable editable = editText.getText();
                        int start = editText.getSelectionStart();
                        //performs the action associated with the code

                        switch (primaryCode) {
                            case (CODE_123_KEY):
                                if (focus != null) {                                    //change to 123 keyboard
                                    keyboardView.setKeyboard(key_123);
                                    keyboardView.setShifted(false);
                                    keyboardState = CODE_123_KEY;
                                }
                                break;
                            case (CODE_ABC_KEY):                                     //change to abc keyboard
                                if (focus != null) {
                                    keyboardView.setKeyboard(key_abc);
                                    keyboardView.setShifted(false);
                                    keyboardState = CODE_ABC_KEY;
                                }
                                break;
                            case (CODE_ABC_KEY_SHIFT):                               //change to shifted abc keyboard
                                if (focus != null) {
                                    if (keyboardState == CODE_ABC_KEY) {
                                        keyboardView.setKeyboard(key_abc_shift);
                                        keyboardState = CODE_ABC_KEY_SHIFT;
                                    } else {
                                        keyboardView.setKeyboard(key_abc);
                                        keyboardState = CODE_ABC_KEY;
                                    }

                                    keyboardView.setShifted(false);
                                }
                                break;
                            case (CODE_MATH_KEY):                                     //change to math keyboard
                                if (focus != null) {
                                    keyboardView.setKeyboard(key_math);
                                    keyboardView.setShifted(false);
                                    keyboardState = CODE_MATH_KEY;
                                }
                                break;
                            case (CODE_ALL_LEFT):
                                editText.setSelection(0);
                                break;
                            case (CODE_ALL_RIGHT):
                                editText.setSelection(editText.length());
                                break;
                            case (CODE_LEFT):
                                if (start > 0) {
                                    editText.setSelection(start - 1);
                                }
                                break;
                            case (CODE_RIGHT):
                                if (start < editText.length()) {
                                    editText.setSelection(start + 1);
                                }
                                break;
                            case (Keyboard.KEYCODE_DELETE):
                                if (editable != null && start > 0) {
                                    editable.delete(start - 1, start);
                                }
                                break;
                            case (CODE_SIN):
                                editable.insert(start, "[sin]()");
                                break;
                            case (CODE_COS):
                                editable.insert(start, "[cos]()");
                                break;
                            case (CODE_TAN):
                                editable.insert(start, "[tan]()");
                                break;
                            case (CODE_ASIN):
                                editable.insert(start, "[asin]()");
                                break;
                            case (CODE_ACOS):
                                editable.insert(start, "[acos]()");
                                break;
                            case (CODE_ATAN):
                                editable.insert(start, "[atan]()");
                                break;
                            case (CODE_LN):
                                editable.insert(start, "[ln]()");
                                break;
                            case (CODE_LOG10):
                                editable.insert(start, "[log10]()");
                                break;
                            case (CODE_LOGN):
                                editable.insert(start, "[logn](num,base)");
                                break;
                            case (CODE_SQRT):
                                editable.insert(start, "[sqrt]()");
                                break;
                            case (CODE_FACTORIAL):
                                editable.insert(start, "[!]()");
                                break;
                            case (CODE_ABS):
                                editable.insert(start, "[abs]()");
                                break;
                            case (CODE_EXP):
                                editable.insert(start, "[e]");
                                break;
                            case (CODE_PI):
                                editable.insert(start, "[pi]");
                                break;
                            default:                                                //basic unicode, insert char
                                editable.insert(start, Character.toString((char) primaryCode));
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


                    );
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

    //TODO implement default screen for each textfield
    /**
     * Registers an edit text field with this keyboard.
     * @param resID The EditText to register with this keyboard.
     */
    public void registerEditText(int resID){
        //object we are working with
        //TODO bug here
        EditText editText = (EditText)host.findViewById(resID);
        //makes keyboard appear on user focus and dissapear when focus lost
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if(hasFocus){
                    editText.setSelection(0,editText.getText().length());
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
                EditText editText = (EditText) v;
//                int start = editText.getSelectionStart();
//                //this works check on touch for youch event
//                editText.setSelection(editText.getText().length());
                showInputKeyboard(v);


            }
        });
        //HARD DISABLES USER KEYBOARD FOR ENTIRE ACTIVITY, NO SWITCHING BACK AND FORTH!
        //much credit to Maarten Pennings see class description for details
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //todo possible solution from online for cursor bug
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Layout layout = ((EditText) v).getLayout();
//                        float x = event.getX() + mText.getScrollX();
//                        int offset = layout.getOffsetForHorizontal(0, x);
//                        if(offset>0)
//                            if(x>layout.getLineMax(0))
//                                mText.setSelection(offset);     // touch was at end of text
//                            else
//                                mText.setSelection(offset - 1);
//                        break;
//                }
//                return true;
                EditText editText = (EditText) v;
                int start = editText.getSelectionStart();
                editText.setSelection(start);
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

}
