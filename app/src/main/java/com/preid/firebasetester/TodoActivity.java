package com.preid.firebasetester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import org.w3c.dom.Text;

public class TodoActivity extends AppCompatActivity {
    private Firebase mFirebaseRef;
    private String mUserId;
    private String itemsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        mFirebaseRef = new Firebase(MainActivity.FIREBASE_URL);
        mUserId = mFirebaseRef.getAuth().getUid();

        itemsUrl = MainActivity.FIREBASE_URL + "/" + mUserId + "/items";

        final ListView itemsListView = (ListView) findViewById(R.id.todo_list_view);
        itemsListView.setAdapter(new FirebaseListAdapter<TodoItem>(
                TodoActivity.this,
                TodoItem.class,
                android.R.layout.simple_list_item_2,
                mFirebaseRef.child(mUserId).child("items")) {

            @Override
            protected void populateView(View view, TodoItem todoItem, int i) {
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(todoItem.getName());
                text2.setText(todoItem.getDescription());
            }
        });

        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFirebaseRef
                        .child(mUserId)
                        .child("items")
                        .orderByChild("name")
                        .equalTo( ((TodoItem) itemsListView.getItemAtPosition(position)).getName())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot
                                        .getChildren()
                                        .iterator()
                                        .next()
                                        .getRef()
                                        .removeValue();
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
            }
        });
    }

    public void onAddNewButtonClick(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.todo_name_edit_text);
        EditText descEditText = (EditText) findViewById(R.id.todo_desc_edit_text);

        String name = nameEditText.getText().toString();
        String desc = descEditText.getText().toString();

        TodoItem item = new TodoItem(name, desc);

        new Firebase(itemsUrl)
                .push()
                .setValue(item);

        nameEditText.setText("");
        descEditText.setText("");
    }
}
