package move4mobile.coders;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by stefankoopman on 17/03/17.
 */

public class ApexTextView extends TextView {
    public ApexTextView(Context context) {
        super(context);
        setTtf();
    }

    public ApexTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTtf();
    }

    public ApexTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTtf();
    }

    private void setTtf(){
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"fonts/apex.ttf");
        setTypeface(tf);
    }
}
