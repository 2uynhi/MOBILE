package com.lehuuquynhnhi.adapters;

import static com.lehuuquynhnhi.models.OrderStatus.COMPLETED;
import static com.lehuuquynhnhi.models.OrderStatus.CUSTOMER_COMPLAIN;
import static com.lehuuquynhnhi.models.OrderStatus.GOING_LOGISTIC;
import static com.lehuuquynhnhi.models.OrderStatus.NOT_YET_PAYMENT;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lehuuquynhnhi.k234111e_mobile.R;
import com.lehuuquynhnhi.models.DataWarehouse;
import com.lehuuquynhnhi.models.Order;

public class OrderAdapter extends ArrayAdapter<Order> {
    Activity context;
    int resource;

    public OrderAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View customView = inflater.inflate(resource, null);
        Order order = this.getItem(position);
        
        TextView txtOrderId = customView.findViewById(R.id.txtOrderId);
        TextView txtOrderDate = customView.findViewById(R.id.txtOrderDate);
        TextView txtStatus = customView.findViewById(R.id.txtStatus);
        TextView txtOrderTotal = customView.findViewById(R.id.txtOrderTotal);
        
        if (order != null) {
            txtOrderId.setText(order.getOrderId());
            txtOrderDate.setText(order.getOrderDate().toString());
            txtStatus.setText(order.getOrderStatus().getDescription());
            txtOrderTotal.setText(String.valueOf(DataWarehouse.sumOfMoney(order)));

            switch (order.getOrderStatus()) {
                case COMPLETED:
                    txtStatus.setTextColor(context.getResources().getColor(R.color.order_completed, null));
                    break;
                case NOT_YET_PAYMENT:
                    txtStatus.setTextColor(context.getResources().getColor(R.color.order_not_yet_payment, null));
                    break;
                case GOING_LOGISTIC:
                    txtStatus.setTextColor(context.getResources().getColor(R.color.order_going_logistic, null));
                    break;
                case CUSTOMER_COMPLAIN:
                    txtStatus.setTextColor(context.getResources().getColor(R.color.order_customer_complain, null));
                    break;
            }
        }
        return customView;
    }
}
