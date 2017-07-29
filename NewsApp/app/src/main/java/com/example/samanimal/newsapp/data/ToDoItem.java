package com.example.samanimal.newsapp.data;

import android.view.View;
import android.widget.CheckBox;
import com.example.samanimal.newsapp.R;

/**
 * Created by Samanimal on 7/23/17.
 */

public class ToDoItem {

    private String description;
    private String dueDate;

    public ToDoItem(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

}
