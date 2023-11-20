package com.webcv.storage;

import com.webcv.storage.arrays.ArrayStorageTest;
import com.webcv.storage.arrays.SortedArrayStorageTest;
import com.webcv.storage.collections.ListStorageTest;
import com.webcv.storage.collections.MapResumeStorageTest;
import com.webcv.storage.collections.MapStorageTest;
import com.webcv.storage.files.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorageTest.class, SortedArrayStorageTest.class, ListStorageTest.class, MapStorageTest.class,
        MapResumeStorageTest.class, ObjectStreamPathStorageTest.class, ObjectStreamStorageTest.class,
        XmlPathStorageTest.class, JsonPathStorageTest.class, DataStreamStorageTest.class, SqlStorageTest.class})
public class AllStoragesTest {

}
