
package com.educa.graphics;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.educa.R;
import com.educa.activity.EditColorMatchExerciseActivity;

public class ColorPickerDialog extends Dialog {
    OnMyDialogResult callback;

    public ColorPickerDialog(Context context) {
        super(context);
        this.setTitle("ColorPickerDialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_dialog);

        final GridView gridViewColors = (GridView) findViewById(R.id.gridViewColors);
        gridViewColors.setAdapter(new ColorPickerAdapter(getContext()));

        // close the dialog on item click
        gridViewColors.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditColorMatchExerciseActivity.color = gridViewColors.getAdapter()
                        .getItem(position).toString();
                EditColorMatchExerciseActivity.setColor((Integer) gridViewColors.getAdapter()
                        .getItem(position));

                /*
                 * colorSelected = (Integer)
                 * gridViewColors.getAdapter().getItem(position);
                 * tv_choose.setText("Color Selected"); LinearLayout
                 * layout_choose = (LinearLayout)
                 * findViewById(R.id.layout_choose);
                 * layout_choose.setBackgroundColor(colorSelected);
                 * layout_choose.setAlpha((float) 0.5);
                 */

                ColorPickerDialog.this.dismiss();
            }
        });
    }

    public void setDialogResult(OnMyDialogResult dialogResult) {
        callback = dialogResult;
    }

    public interface OnMyDialogResult {
        void finish(String result);
    }
}
