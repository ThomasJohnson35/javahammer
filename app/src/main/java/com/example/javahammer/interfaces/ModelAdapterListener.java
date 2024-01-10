package com.example.javahammer.interfaces;

import com.example.javahammer.data.Model;
import com.example.javahammer.data.ModelComposition;

public interface ModelAdapterListener {

    void removeModel(ModelComposition modelComposition, Model model);
    void unitDataChanged();
}
