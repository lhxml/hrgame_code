package com.example.andriod.contentproviderdemo;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by andriod on 2016/11/7.
 */

public class Provider {
    // 这个是每个Provider的标识，在Manifest中使用
    public static final String AUTHORITY = "com.example.andriod.contentproviderdemo.PersonProvider";

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jacp.demo";

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jacp.demo";
    /**
     * 跟Person表相关的常量
     * @author jacp
     *
     */
    public static final class PersonColumns implements BaseColumns {
        // CONTENT_URI跟数据库的表关联，最后根据CONTENT_URI来查询对应的表
        public static final Uri CONTENT_URI = Uri.parse("content://"+ AUTHORITY +"/person");
        public static final String TABLE_NAME = "person";
        public static final String DEFAULT_SORT_ORDER = "age desc";

        public static final String NAME = "name";
        public static final String ID = "id";

    }
}
