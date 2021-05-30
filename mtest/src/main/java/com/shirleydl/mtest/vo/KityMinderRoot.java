package com.shirleydl.mtest.vo;

import java.util.List;

public class KityMinderRoot {
    protected KityMinderData data;
    protected List<KityMinderRoot> children;

    public KityMinderData getData() {
        return data;
    }

    public void setData(KityMinderData data) {
        this.data = data;
    }

    public List<KityMinderRoot> getChildren() {
        return children;
    }

    public void setChildren(List<KityMinderRoot> children) {
        this.children = children;
    }
}
