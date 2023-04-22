package com.basejava.storage;

import com.basejava.storage.arrays.ArrayStorage;
import com.basejava.storage.arrays.SortedArrayStorage;
import com.basejava.storage.collections.ListStorage;
import com.basejava.storage.collections.MapStorage;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorage.class, SortedArrayStorage.class, ListStorage.class, MapStorage.class})
public class AllStoragesTest {

}
