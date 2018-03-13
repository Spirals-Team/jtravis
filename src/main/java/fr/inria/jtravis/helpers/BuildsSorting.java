package fr.inria.jtravis.helpers;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class BuildsSorting {
    private enum SortOptions {
        ID,
        FINISHED_AT,
        STARTED_AT;

        private boolean desc;

        protected boolean isDesc() {
            return desc;
        }

        protected SortOptions setDesc(boolean desc) {
            this.desc = desc;
            return this;
        }
    }

    private TreeSet<SortOptions> queryBuilder;

    public BuildsSorting() {
        this.queryBuilder = new TreeSet<>();

        for (SortOptions sortOptions : SortOptions.values()) {
            sortOptions.setDesc(false);
        }
    }

    public BuildsSorting byId() {
        this.queryBuilder.add(SortOptions.ID);
        return this;
    }

    public BuildsSorting byIdDesc() {
        this.queryBuilder.add(SortOptions.ID.setDesc(true));
        return this;
    }

    public BuildsSorting byFinishedAt() {
        this.queryBuilder.add(SortOptions.FINISHED_AT);
        return this;
    }

    public BuildsSorting byFinishedAtDesc() {
        this.queryBuilder.add(SortOptions.FINISHED_AT.setDesc(true));
        return this;
    }

    public BuildsSorting byStartedAt() {
        this.queryBuilder.add(SortOptions.STARTED_AT);
        return this;
    }

    public BuildsSorting byStartedAtDesc() {
        this.queryBuilder.add(SortOptions.ID.setDesc(true));
        return this;
    }

    public String build() {
        if (this.queryBuilder.size() == 0) {
            return "";
        } else {
            List<String> result = new ArrayList<>();
            for (SortOptions sortOption : this.queryBuilder) {
                String sort = sortOption.name().toLowerCase();
                if (sortOption.isDesc()) {
                    sort += ":desc";
                }
                result.add(sort);
            }
            return StringUtils.join(result, ",");
        }
    }
}
