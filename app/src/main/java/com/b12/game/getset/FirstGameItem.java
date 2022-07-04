package com.b12.game.getset;

import java.util.Objects;

public class FirstGameItem {
    private int imageCount;

    public FirstGameItem() {

    }
    public FirstGameItem( int imageCount) {
        this.imageCount = imageCount;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstGameItem that = (FirstGameItem) o;
        return imageCount == that.imageCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageCount);
    }
}
