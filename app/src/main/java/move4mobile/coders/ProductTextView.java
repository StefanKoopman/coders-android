package move4mobile.coders;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by stefankoopman on 24/03/17.
 */

public class ProductTextView extends TextView {
    public ProductTextView(Context context) {
        super(context);
        setTtf();
    }

    public ProductTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTtf();
    }

    public ProductTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTtf();
    }

    private void setTtf(){
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"fonts/product_bold.ttf");
        setTypeface(tf);
    }
}
