package com.example.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.ticket.Ticket;

public class TicketDiffItemCallback extends DiffUtil.ItemCallback<Ticket> {
    @Override
    public boolean areItemsTheSame(@NonNull Ticket oldItem, @NonNull Ticket newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Ticket oldItem, @NonNull Ticket newItem) {
        return oldItem.getType().equals(newItem.getType())
                && oldItem.getState().equals(newItem.getState())
                && oldItem.getPriority().equals(newItem.getPriority())
                && oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getDescription().equals(newItem.getDescription());
    }
}
