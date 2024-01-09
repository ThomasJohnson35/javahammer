package com.example.javahammer.interfaces;

import android.widget.PopupMenu;

import com.example.javahammer.data.Roster;

public interface RosterAdapterListener {

    void onItemClick(Roster roster);
    void onEllipsisClick(Roster roster, PopupMenu popup);
}
