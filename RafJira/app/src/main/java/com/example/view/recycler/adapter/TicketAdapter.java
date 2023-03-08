package com.example.view.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raf_jira.R;
import com.example.ticket.Ticket;
import com.example.ticket.ticketType.TicketType;

public class TicketAdapter extends ListAdapter<Ticket, TicketAdapter.TicketHolder> {

    private TicketClickInterface ticketClickInterface;

    public TicketAdapter(@NonNull DiffUtil.ItemCallback<Ticket> diffCallback, TicketClickInterface ticketClickInterface) {
        super(diffCallback);
        this.ticketClickInterface = ticketClickInterface;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ticket_item, parent, false);
        return new TicketHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class TicketHolder extends RecyclerView.ViewHolder{

        public TicketHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Ticket ticket){
            ImageView imageView = itemView.findViewById(R.id.card_view_image);
            TextView title = itemView.findViewById(R.id.card_view_textview1);
            TextView description = itemView.findViewById(R.id.card_view_textview2);
            Button btn1 = itemView.findViewById(R.id.button1);
            Button btn2 = itemView.findViewById(R.id.button2);;
            Button btn3 = itemView.findViewById(R.id.button3);;

            if(ticket.getType() == TicketType.ENHANCEMENT)
               imageView.setImageResource(R.drawable.ic_enhancement_icon);
            else if(ticket.getType() == TicketType.BUG)
                imageView.setImageResource(R.drawable.ic_bug_icon);
            title.setText(ticket.getTitle());
            description.setText(ticket.getDescription());
            btn1.setOnClickListener(view -> {
                if(ticketClickInterface == null) return;
                ticketClickInterface.onDelete(getCurrentList().get(getBindingAdapterPosition()).getId());
            });
            btn2.setOnClickListener(view -> {
                if(ticketClickInterface == null) return;
                ticketClickInterface.fromTodoToInProgress(getCurrentList().get(getBindingAdapterPosition()).getId());
            });
        }
    }

    public interface TicketClickInterface {
        void onDelete(int id);
        void fromTodoToInProgress(int id);
        void fromInProgressToTodo(int id);
    }

}
