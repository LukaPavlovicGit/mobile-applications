package com.example.view.viewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raf_jira.R;
import com.example.ticket.Ticket;
import com.example.ticket.ticketType.TicketType;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketHolder> {

    private List<Ticket> tickets = new ArrayList<>();

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ticket_item, parent, false);
        return new TicketHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        holder.bind(tickets.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickets.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
        notifyDataSetChanged();
    }

    public Ticket getTicketAt(int position){
        return tickets.get(position);
    }


    class TicketHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title;
        private TextView description;

        public TicketHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.card_view_image);
            title = itemView.findViewById(R.id.card_view_textview);
            description = itemView.findViewById(R.id.card_view_textview2);
        }

        public void bind(Ticket ticket){
            if(ticket.getType() == TicketType.ENHANCEMENT)
               imageView.setImageResource(R.drawable.ic_enhancement_icon);
            else if(ticket.getType() == TicketType.BUG)
                imageView.setImageResource(R.drawable.ic_bug_icon);

            title.setText(ticket.getTitle());
            description.setText(ticket.getDescription());
        }

    }

}
