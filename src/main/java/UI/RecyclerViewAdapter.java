package UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyneedsapp.R;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;

import Data.DatabaseHandler;
import Model.Item;

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent,  false);
        return new ViewHolder(view, context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
       Item item = itemList.get(position);
        holder.ItemName.setText("Item: " + item.getItemName());
        holder.ItemQty.setText("Qty: " + Integer.toString(item.getItemQuantity()));
        holder.ItemSize.setText("Size: " + Integer.toString(item.getItemSize()));
        holder.ItemColour.setText("Colour: " + item.getItemColour());
        holder.ItemDate.setText("Added on: " +  item.getDateItemAdded());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ItemName;
        public TextView ItemQty;
        public TextView ItemSize;
        public TextView ItemColour;
        public TextView ItemDate;
        public Button btnEdit, btnDelete;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
             context = ctx;
             ItemName = itemView.findViewById(R.id.Item_Name);
             ItemQty = itemView.findViewById(R.id.Item_Qty);
             ItemSize = itemView.findViewById(R.id.Item_Size);
             ItemColour = itemView.findViewById(R.id.Item_Colour);
             ItemDate = itemView.findViewById(R.id.Item_Date);
             btnEdit = itemView.findViewById(R.id.btnEdit);
             btnDelete = itemView.findViewById(R.id.btnDelete);

             btnEdit.setOnClickListener(this);
             btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position;
            position = getAdapterPosition();
            Item item = itemList.get(position);
            switch (view.getId())
            {
                case R.id.btnEdit :
                    //Edit Item
                    editItem(item);
                    break;
                case R.id.btnDelete :
                    //Delete Item
                    deleteItem(item.getId());
                    break;
            }
        }

        private void editItem(final Item newItem) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup, null);

            Button btnSave;
            final EditText etBabyItem, etItemQuantity, etItemColour, etItemSize;
            TextView title;

            etBabyItem = view.findViewById(R.id.etBabyItem);
            etItemQuantity = view.findViewById(R.id.etItemQuantity);
            etItemColour = view.findViewById(R.id.etItemColour);
            etItemSize = view.findViewById(R.id.etItemSize);
            title = view.findViewById(R.id.tvTitle);
            title.setText(R.string.edit_item);

            btnSave = view.findViewById(R.id.btnSave);
            btnSave.setText(R.string.update_btn);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();
            Item item = itemList.get(getAdapterPosition());

            etBabyItem.setText(newItem.getItemName());
            etItemQuantity.setText(Integer.toString(newItem.getItemQuantity()));
            etItemColour.setText(newItem.getItemColour());
            etItemSize.setText(Integer.toString(newItem.getItemSize()));

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Update our Item
                    DatabaseHandler db = new DatabaseHandler(context);

                    newItem.setItemName(etBabyItem.getText().toString().trim());
                    newItem.setItemColour(etItemColour.getText().toString().trim());
                    newItem.setItemQuantity(Integer.parseInt(etItemQuantity.getText().toString().trim()));
                    newItem.setItemSize(Integer.parseInt(etItemSize.getText().toString().trim()));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                        if (etBabyItem.getText().toString().isEmpty() && etItemQuantity.getText().toString().isEmpty())
                        {
                            Snackbar.make(view,"Fields Required", Snackbar.LENGTH_SHORT).show();
                        }
                        else
                        {
                            db.updateItem(newItem);
                            //Important! to see the changes made to the item
                            notifyItemChanged(getAdapterPosition(), newItem);
                        }
                    }

                    dialog.dismiss();

                }
            });
        }

        private void deleteItem(final int id) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.confirmation, null);

            Button btnYes = view.findViewById(R.id.btnYes);
            Button btnNo = view.findViewById(R.id.btnNo);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


        }
    }


    }
