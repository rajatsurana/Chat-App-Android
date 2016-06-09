package com.example.edel.apptab;

import java.util.Date;

/**
 * Created by Apple on 20/02/16.
 */
public class ListUsersActivity {

    private final String name;
    private final Date postDate;
    private final int icon;

    public ListUsersActivity(String name, Date postDate, int icon) {
        this.name = name;
        this.postDate = postDate;
        this.icon = icon;
    }
    public String getName() {
        return  this.name;
    }

    /**
     * @return Post date of news entry
     */
    public Date getPostDate() {
        return postDate;
    }

    /**
     * @return Icon of this news entry
     */
    public int getIcon() {
        return this.icon;
    }
}
