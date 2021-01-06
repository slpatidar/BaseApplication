package com.sp.base.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DummyResponseModel {
    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("next")
    @Expose
    private String next;

    @SerializedName("results")
    @Expose
    public ArrayList<ListData> results;

    class ListData {
        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("url")
        @Expose
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "ListData{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public ArrayList<ListData> getResults() {
        return results;
    }

    public void setResults(ArrayList<ListData> results) {
        this.results = results;
    }
}
