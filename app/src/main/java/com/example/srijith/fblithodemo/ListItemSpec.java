package com.example.srijith.fblithodemo;

import android.graphics.Color;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.FromEvent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.fresco.FrescoImage;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by Srijith on 29-04-2017.
 */

@LayoutSpec
public class ListItemSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext c,
            @Prop String name,
            @Prop String phoneNumber,
            @Prop String photo) {

        final ComponentLayout column = Column.create(c)
                .marginDip(YogaEdge.LEFT, 16)
                .child(
                        Text.create(c)
                                .text(name)
                                .textSizeSp(24)
                )
                .child(
                        Text.create(c)
                                .text(phoneNumber)
                                .textSizeSp(16)
                )
                .build();

        final DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(photo)
                .build();

        return Row.create(c)
                .backgroundAttr(android.R.attr.selectableItemBackground)
                .paddingDip(YogaEdge.ALL, 16)
                .child(
                        FrescoImage.create(c)
                                .controller(controller)
                                .failureImageRes(R.drawable.ic_account_box_black_48dp)
                                .placeholderImageRes(R.drawable.ic_account_box_black_48dp)
                                .withLayout()
                                .backgroundColor(Color.GRAY)
                                .widthDip(80)
                                .heightDip(80)
                )
                .child(column)
                .clickHandler(ListItem.onClick(c))
                .build();
    }

    @OnEvent(ClickEvent.class)
    static void onClick(ComponentContext c,
                        @FromEvent View view) {

    }

}
