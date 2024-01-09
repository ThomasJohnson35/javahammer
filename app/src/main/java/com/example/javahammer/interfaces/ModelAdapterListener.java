package com.example.javahammer.interfaces;

import com.example.javahammer.data.Model;

public interface ModelAdapterListener {

    void onModelClick(Model model);
    void unitDataChanged();
}
