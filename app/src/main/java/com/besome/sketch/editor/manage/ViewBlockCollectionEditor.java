package com.besome.sketch.editor.manage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.editor.view.LogicEditorScrollView;

public class ViewBlockCollectionEditor extends LogicEditorScrollView {
    public Context i;
    public BlockPane j;
    public boolean k;
    public int[] l;

    public ViewBlockCollectionEditor(Context context) {
        super(context);
        this.k = true;
        this.l = new int[2];
        a(context);
    }

    public ViewBlockCollectionEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.k = true;
        this.l = new int[2];
        a(context);
    }

    @Override // com.besome.sketch.editor.view.LogicEditorScrollView
    public void a(Context context) {
        this.i = context;
        this.j = new BlockPane(this.i);
        this.j.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        addView(this.j);
    }

    public BlockPane getBlockPane() {
        return this.j;
    }

    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        if (this.k) {
            this.j.getLayoutParams().width = i4 - i2;
            this.j.getLayoutParams().height = i5 - i3;
            this.j.b();
            this.k = false;
        }
    }
}
