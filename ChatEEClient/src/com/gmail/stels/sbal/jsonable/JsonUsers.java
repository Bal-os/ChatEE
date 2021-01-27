package com.gmail.stels.sbal.jsonable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUsers {
    private final List<User> list = new ArrayList<>();

    public JsonUsers(List<User> sourceList) {
        for (User u: sourceList)
            list.add(u);
    }

    public List<User> getList() {
        return Collections.unmodifiableList(list);
    }
}
