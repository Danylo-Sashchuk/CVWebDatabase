package com.basejava.storage;

import com.basejava.storage.arrays.ArrayStorageTest;
import com.basejava.storage.arrays.SortedArrayStorageTest;
import com.basejava.storage.collections.ListStorageTest;
import com.basejava.storage.collections.MapStorageTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorageTest.class, SortedArrayStorageTest.class, ListStorageTest.class, MapStorageTest.class})
public class AllStoragesTest {

}
