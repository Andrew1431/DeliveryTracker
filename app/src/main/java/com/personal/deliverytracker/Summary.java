package com.personal.deliverytracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Summary extends ActionBarActivity {
    private ListView list;
    private DeliveryArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Intent intent = getIntent();
        list = (ListView) findViewById(R.id.deliveryItems);

        final ArrayList<Delivery> data = intent.getParcelableArrayListExtra("deliveryData");
        adapter = new DeliveryArrayAdapter(this, data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editor = new Intent(getApplicationContext(), EditDeliveryInformation.class);
                editor.putParcelableArrayListExtra("deliveries", data);
                editor.putExtra("position", position);
                startActivityForResult(editor, 1);
            }
        });

        TextView d = (TextView) findViewById(R.id.deliveries);
        TextView o = (TextView) findViewById(R.id.outs);
        TextView m = (TextView) findViewById(R.id.total);

        d.setText(String.valueOf(data.size()));
        int count = 0;
        for (int x = 0; x < data.size(); x++) {
            if (data.get(x).isOut()) count++;
        }
        o.setText(String.valueOf(count));
        float total = (float)(data.size() * 1.10);
        for (int x = 0; x < data.size(); x++) {
            total += data.get(x).getTip();
            if (data.get(x).isOut()) total += 0.50f;
        }
        m.setText("$" + String.format("%.2f", total));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (super.onOptionsItemSelected(item)) return true; else return false;
    }

    private class DeliveryArrayAdapter extends ArrayAdapter<Delivery> {
        private final Context context;
        private final ArrayList<Delivery> values;

        public DeliveryArrayAdapter(Context context, ArrayList<Delivery> values) {
            super(context, R.layout.layout_delivery_items, values);
            this.context = context;
            this.values = values;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.layout_delivery_items, parent, false);
            TextView firstLine = (TextView) rowView.findViewById(R.id.firstLine);
            TextView secondLine = (TextView) rowView.findViewById(R.id.secondLine);
            firstLine.setText("Order #" + values.get(position).getDeliveryNumber());
            secondLine.setText(values.get(position).toString());
            return rowView;
        }
    }
}
