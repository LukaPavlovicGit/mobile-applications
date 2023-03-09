package com.example.view.recycler.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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

import com.example.constants.Constants;
import com.example.raf_jira.R;
import com.example.ticket.Ticket;
import com.example.ticket.enumTicket.TicketType;
import com.example.view.fragments.DoneTicketsFragment;
import com.example.view.fragments.TodoTicketsFragment;

public class TicketAdapter extends ListAdapter<Ticket, TicketAdapter.TicketHolder> {

    private TicketClickInterface ticketClickInterface;

    public TicketAdapter(@NonNull DiffUtil.ItemCallback<Ticket> diffCallback, TicketClickInterface ticketClickInterface) {
        super(diffCallback);
        this.ticketClickInterface = ticketClickInterface;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_recycler_view, parent, false);
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
            Button btn2 = itemView.findViewById(R.id.button2);
            Button btn3 = itemView.findViewById(R.id.button3);
            Button btn4 = itemView.findViewById(R.id.button4);

            if(ticket.getType() == TicketType.ENHANCEMENT)
               imageView.setImageResource(R.drawable.ic_enhancement_icon);
            else if(ticket.getType() == TicketType.BUG)
                imageView.setImageResource(R.drawable.ic_bug_icon);
            title.setText(ticket.getTitle());
            description.setText(ticket.getDescription());

            if(getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                itemView.setOnClickListener(view -> ticketClickInterface.itemClicked(itemView, getBindingAdapterPosition()));

            if(ticketClickInterface instanceof DoneTicketsFragment){
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
            }
            else if(ticketClickInterface instanceof TodoTicketsFragment){
                TodoInterface todoInterface = (TodoInterface) ticketClickInterface;
                if(getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    btn1.setOnClickListener(view -> todoInterface.fromTodoToInProgress(getCurrentList().get(getBindingAdapterPosition()).getId()));
                    btn2.setOnClickListener(view -> todoInterface.onDelete(getCurrentList().get(getBindingAdapterPosition()).getId()));
                }

                SharedPreferences sharedPreferences = ((TodoTicketsFragment) ticketClickInterface).getActivity().getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
                String username = sharedPreferences.getString(Constants.USERNAME_KEY, "");
                if(!username.startsWith("admin"))
                    btn2.setVisibility(View.INVISIBLE);

                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
            }
            else if(ticketClickInterface instanceof InProgressInterface){
                InProgressInterface inProgressInterface = (InProgressInterface) ticketClickInterface;
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                if(getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    btn3.setOnClickListener(view -> inProgressInterface.fromInProgressToTodo(getCurrentList().get(getBindingAdapterPosition()).getId()));
                    btn4.setOnClickListener(view -> inProgressInterface.fromInProgressToDone(getCurrentList().get(getBindingAdapterPosition()).getId()));
                }
            }
        }
    }

    public interface TicketClickInterface {
        void itemClicked(View v, int position);
    }

    public interface TodoInterface extends TicketClickInterface {
        void onDelete(int id);
        void fromTodoToInProgress(int id);
    }
    public interface InProgressInterface extends TicketClickInterface {
        void fromInProgressToTodo(int id);
        void fromInProgressToDone(int id);
    }

}
