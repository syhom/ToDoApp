package com.example.todoapp;

import java.io.Serializable;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Items")
public class Item extends Model implements Serializable {
	private static final long serialVersionUID = 5177222050535318633L;
	
    // This is how you avoid duplicates
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int remoteId;
    
    // If name is omitted, then the field name is used.
    @Column(name = "Task")
    public String task;

    public Item(){
    	super();
    }
    
    public Item(int remoteId, String task){
        super();
        this.remoteId = remoteId;
        this.task = task;
    }
    
    public static List<Item> getAll() {
        // This is how you execute a query
        return new Select()
          .from(Item.class)
          .execute();
    }
    
    @Override
    public String toString() {
        return task;
    }
}