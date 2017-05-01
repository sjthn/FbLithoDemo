package com.example.srijith.fblithodemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.widget.Toast;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentInfo;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.Card;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int REQUEST_CODE_READ_CONTACTS_PERMISSION = 100;

    private static final int CONTACT_ID_INDEX = 0;
    private static final int LOOKUP_KEY_INDEX = 1;
    private static final int DISPLAY_NAME_INDEX = 2;
    private static final int NUMBER_INDEX = 3;
    private static final int PHOTO_THUMBNAIL_URI_INDEX = 4;

    private static final String[] PROJECTION = {
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
    };

    private ComponentContext componentContext;
    private RecyclerBinder recyclerBinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        componentContext = new ComponentContext(this);

        recyclerBinder = new RecyclerBinder(
                componentContext,
                new LinearLayoutInfo(this, OrientationHelper.VERTICAL, false));

        Component<Recycler> recyclerComponent = Recycler.create(componentContext)
                .binder(recyclerBinder)
                .build();

        addContents(recyclerBinder, componentContext, null);

        final LithoView lithoView = LithoView.create(
                this,
                recyclerComponent);


        setContentView(lithoView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_READ_CONTACTS_PERMISSION);
            return;
        }

        getLoaderManager().initLoader(0, null, this);

    }

    private static void addContents(RecyclerBinder recyclerBinder, ComponentContext context, Cursor data) {

        if (data == null) {
            recyclerBinder.insertItemAt(
                    0,
                    ProgressBar.create(context)
                            .build());
            return;
        }

        if (recyclerBinder.getItemCount() == 1) {
            recyclerBinder.removeItemAt(0);
        }

        data.moveToFirst();
        for (int i = 0; i < data.getCount(); i++) {
            String name = data.getString(
                    data.getColumnIndex(
                            data.getColumnName(DISPLAY_NAME_INDEX)));
            String number = data.getString(
                    data.getColumnIndex(
                            data.getColumnName(NUMBER_INDEX)));
            String photo = data.getString(
                    data.getColumnIndex(
                            data.getColumnName(PHOTO_THUMBNAIL_URI_INDEX)));

            recyclerBinder.insertItemAt(
                    i,
                    ComponentInfo.create()
                            .component(Card.create(context)
                                    .content(
                                            ListItem.create(context)
                                                    .name(name)
                                                    .phoneNumber(number)
                                                    .photo(photo)
                                                    .build()
                                    )
                                    .build())
                            .build());

            data.moveToNext();

        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                this,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        addContents(recyclerBinder, componentContext, data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_CONTACTS_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLoaderManager().initLoader(0, null, this);
            } else {
                Toast.makeText(
                        this,
                        "App need permission to show list of contacts",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
