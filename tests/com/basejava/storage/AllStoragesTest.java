package com.basejava.storage;

import com.basejava.storage.arrays.ArrayStorageTest;
import com.basejava.storage.arrays.SortedArrayStorageTest;
import com.basejava.storage.collections.ListStorageTest;
import com.basejava.storage.collections.MapResumeStorageTest;
import com.basejava.storage.collections.MapStorageTest;
import com.basejava.storage.files.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorageTest.class, SortedArrayStorageTest.class, ListStorageTest.class, MapStorageTest.class,
        MapResumeStorageTest.class, ObjectStreamPathStorageTest.class, ObjectStreamStorageTest.class,
        XmlPathStorageTest.class, JsonPathStorageTest.class, DataStreamStorageTest.class})
public class AllStoragesTest {

}
