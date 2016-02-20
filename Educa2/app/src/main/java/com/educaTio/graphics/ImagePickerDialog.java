
package com.educaTio.graphics;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.educaTio.R;
import com.educaTio.activity.EditImageMatchExerciseActivity;;

public class ImagePickerDialog extends Dialog {
    OnMyDialogResult callback;

    public ImagePickerDialog(Context context) {
        super(context);
        this.setTitle("ImagePickerDialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_picker_dialog);

        final GridView gridViewColors = (GridView) findViewById(R.id.gridViewImages);
        gridViewColors.setAdapter(new ImagePickerAdapter(getContext()));

        // close the dialog on item click
        gridViewColors.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditImageMatchExerciseActivity.color = gridViewColors.getAdapter()
                        .getItem(position).toString();
                EditImageMatchExerciseActivity.setColor((Integer) gridViewColors.getAdapter()
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

                ImagePickerDialog.this.dismiss();
            }
        });
    }

    public void setDialogResult(OnMyDialogResult onMyDialogResult) {
        callback = (OnMyDialogResult) onMyDialogResult;
    }

    public interface OnMyDialogResult {
        void finish(String result);
    }
}
