package com.example.srijith.fblithodemo;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.widget.Progress;
import com.facebook.yoga.YogaAlign;

/**
 * Created by Srijith on 01-05-2017.
 */

@LayoutSpec
public class ProgressBarSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(ComponentContext c) {

        return Column.create(c)
                .child(
                        Progress.create(c)
                                .colorAttr(R.attr.colorPrimary)
                                .withLayout()
                                .widthDip(48)
                                .heightDip(48)
                                .alignSelf(YogaAlign.CENTER)
                )
                .build();

    }

}
